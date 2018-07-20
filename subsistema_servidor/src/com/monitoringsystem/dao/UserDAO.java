package com.monitoringsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.monitoringsystem.config.BDConfig;
import com.monitoringsystem.entity.User;

public class UserDAO {
	
	public List<User> listUser() throws Exception{
        List<User> listOfUser = new ArrayList<User>();

        Connection conexao = BDConfig.getConnection();

        String sql = "SELECT * FROM USERS";

        PreparedStatement statement = conexao.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
        	User user = new User();
        	user.setId(rs.getInt("ID"));
        	user.setUsername(rs.getString("USERNAME"));
        	user.setEmail(rs.getString("EMAIL"));
        	user.setPassword(rs.getString("PASSWORD"));
        	user.setPhone(rs.getString("PHONE"));

        	listOfUser.add(user);
        }

        return listOfUser;
    }
	
	public User searchUserById(int idUser) throws Exception{
		User user = null;

        Connection conexao = BDConfig.getConnection();

        String sql = "SELECT * FROM USERS WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, idUser);
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
        	user = new User();
        	user.setId(rs.getInt("ID"));
        	user.setUsername(rs.getString("USERNAME"));
        	user.setEmail(rs.getString("EMAIL"));
        	user.setPassword(rs.getString("PASSWORD"));
        	user.setPhone(rs.getString("PHONE"));
        }

        return user;
    }
	
	public boolean addUser(User user) throws Exception{
		boolean userSaved = false;
		
    	//int idGenerated = 0;
    	
        Connection conexao = BDConfig.getConnection();

        String sql = "INSERT INTO USERS(USERNAME, EMAIL, PASSWORD, PHONE) VALUES(?, ?, ?, ?)";

        PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getPhone());
        statement.execute();
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
        	//idGenerated = rs.getInt(1);
        	userSaved = true;
        }
		
        
        return userSaved;
    }
	
	public boolean editUser(User user, int idUser) throws Exception{
		
		boolean userSaved = false;
        Connection conexao = BDConfig.getConnection();

        String sql = "UPDATE USERS SET USERNAME = ?, EMAIL = ?, PASSWORD = ?, PHONE = ? WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getPhone());
        statement.setInt(5, idUser);
        
        int i = statement.executeUpdate();
        
        if(i > 0){
        	userSaved = true;
        }
        
        return userSaved;
    }
	
	public void deleteUser(int idUser) throws Exception{
        Connection conexao = BDConfig.getConnection();

        String sql = "DELETE FROM USERS WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, idUser);
        statement.execute();
    }
	
	public boolean userLogin(String email, String password) throws Exception{
		boolean userFound = false;
		Connection conexao = BDConfig.getConnection();
		
		 String sql = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";
		 
		 PreparedStatement statement = conexao.prepareStatement(sql);
		 statement.setString(1, email);
		 statement.setString(2, password);
		 
		 ResultSet rs = statement.executeQuery();
		 
		 while(rs.next()) {
			 userFound = true;
		 }
		
		return userFound;
	}
	
	public User findUserByEmail(String email) throws Exception{
		Connection conexao = BDConfig.getConnection();
		
		String sql = "SELECT * FROM USERS WHERE EMAIL = ?";
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);
        ResultSet rs = statement.executeQuery();
        
        User user = new User();
        
        while(rs.next()) {
        	user = new User();
        	user.setId(rs.getInt("ID"));
        	user.setUsername(rs.getString("USERNAME"));
        	user.setEmail(rs.getString("EMAIL"));
        	user.setPassword(rs.getString("PASSWORD"));
        	user.setPhone(rs.getString("PHONE"));
        }

        return user;
	}
	
}
