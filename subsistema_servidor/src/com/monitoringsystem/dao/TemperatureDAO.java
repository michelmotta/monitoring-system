package com.monitoringsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.monitoringsystem.config.BDConfig;
import com.monitoringsystem.entity.Config;
import com.monitoringsystem.entity.Graph;
import com.monitoringsystem.entity.Temperature;

public class TemperatureDAO {
	
	private Config config;
	private ConfigDAO configDAO;
	
	public TemperatureDAO() {
		this.config = new Config();
		this.configDAO = new ConfigDAO();
	}

	public List<Temperature> listTemperature() throws Exception{
        List<Temperature> listOfTemperature = new ArrayList<Temperature>();

        Connection conexao = BDConfig.getConnection();

        String sql = "SELECT * FROM TEMPERATURES";

        PreparedStatement statement = conexao.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
        	Temperature temperature = new Temperature();
        	temperature.setId(rs.getInt("ID"));
            temperature.setServer_id(rs.getInt("SERVER_ID"));
            temperature.setServer_name(rs.getString("SERVER_NAME"));
            temperature.setTemperature(rs.getDouble("TEMPERATURE"));
            temperature.setHumidity(rs.getDouble("HUMIDITY"));
            temperature.setTime(rs.getString("TIME"));

            listOfTemperature.add(temperature);
        }

        return listOfTemperature;
    }
	
	public List<Temperature> listTemperatureWithLimit(int limit) throws Exception{
        List<Temperature> listOfTemperature = new ArrayList<Temperature>();

        Connection conexao = BDConfig.getConnection();

        String sql = "SELECT * FROM TEMPERATURES ORDER BY ID DESC LIMIT ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, limit);
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
        	Temperature temperature = new Temperature();
        	temperature.setId(rs.getInt("ID"));
            temperature.setServer_id(rs.getInt("SERVER_ID"));
            temperature.setServer_name(rs.getString("SERVER_NAME"));
            temperature.setTemperature(rs.getDouble("TEMPERATURE"));
            temperature.setHumidity(rs.getDouble("HUMIDITY"));
            
            Timestamp dateTime = rs.getTimestamp("TIME");
        	String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dateTime.getTime());
        	
            temperature.setTime(date);

            listOfTemperature.add(temperature);
        }

        return listOfTemperature;
    }
	
	public Temperature searchTemperatureById(int idTemperature) throws Exception{
		Temperature temperature = null;

        Connection conexao = BDConfig.getConnection();

        String sql = "SELECT * FROM TEMPERATURES WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, idTemperature);
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
        	temperature = new Temperature();
            temperature.setId(rs.getInt("ID"));
            temperature.setServer_id(rs.getInt("SERVER_ID"));
            temperature.setServer_name(rs.getString("SERVER_NAME"));
            temperature.setTemperature(rs.getDouble("TEMPERATURE"));
            temperature.setHumidity(rs.getDouble("HUMIDITY"));
            temperature.setTime(rs.getString("TIME"));
        }

        return temperature;
    }
	
	public int addTemperature(Temperature temperature) throws Exception{
    	int idGenerated = 0;
        Connection conexao = BDConfig.getConnection();

        String sql = "INSERT INTO TEMPERATURES(SERVER_ID, SERVER_NAME, TEMPERATURE, HUMIDITY, TIME) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, temperature.getServer_id());
        statement.setString(2, temperature.getServer_name());
        statement.setDouble(3, temperature.getTemperature());
        statement.setDouble(4, temperature.getHumidity());
        statement.setString(5, temperature.getTime());
        statement.execute();
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
        	idGenerated = rs.getInt(1);
        }
        
        return idGenerated;
    }
	
	public void editTemperature(Temperature temperature, int idTemperature) throws Exception{
        Connection conexao = BDConfig.getConnection();

        String sql = "UPDATE TEMPERATURES SET SERVER_ID = ?, SERVER_NAME = ?, TEMPERATURE = ?, HUMIDITY = ?, TIME = ? WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, temperature.getServer_id());
        statement.setString(2, temperature.getServer_name());
        statement.setDouble(3, temperature.getTemperature());
        statement.setDouble(4, temperature.getHumidity());
        statement.setString(5, temperature.getTime());
        statement.setInt(6, idTemperature);
        statement.execute();
    }
	
	public void deleteTemperature(int idTemperature) throws Exception{
        Connection conexao = BDConfig.getConnection();

        String sql = "DELETE FROM TEMPERATURES WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, idTemperature);
        statement.execute();
    }
	
	public List<Temperature> checkTemperature(int limit) throws Exception{
        List<Temperature> listOfTemperature = new ArrayList<Temperature>();

        Connection conexao = BDConfig.getConnection();

        String sql = "SELECT * FROM TEMPERATURES ORDER BY ID DESC LIMIT ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, limit);
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
        	Temperature temperature = new Temperature();
        	temperature.setId(rs.getInt("ID"));
            temperature.setServer_id(rs.getInt("SERVER_ID"));
            temperature.setServer_name(rs.getString("SERVER_NAME"));
            temperature.setTemperature(rs.getDouble("TEMPERATURE"));
            temperature.setHumidity(rs.getDouble("HUMIDITY"));
            temperature.setTime(rs.getString("TIME"));

            listOfTemperature.add(temperature);
        }

        return listOfTemperature;
    }
	
	public int checkMaximumTemperatureLimit(Double finalAverageTemperature) throws Exception {
		int response = 0;
		config = configDAO.searchConfigById(1);
		
		if(finalAverageTemperature > config.getMaximumTemperatureLimit()){
			response = 1;
		}
		
		return response;
	}
	
	public int checkMinimumTemperatureLimit(Double finalAverageTemperature) throws Exception{
		int response = 0;
		config = configDAO.searchConfigById(1);
		
		if(finalAverageTemperature < config.getMinimumTemperatureLimit()){
			response = 1;
		}
		
		return response;
	}
	
	public int checkMaximumHumidityLimit(Double finalAverageHumidity) throws Exception{
		int response = 0;
		config = configDAO.searchConfigById(1);
		
		if(finalAverageHumidity > config.getMaximumHumidityLimit()){
			response = 1;
		}
		
		return response;
	}
	
	public int checkMinimumHumidityLimit(Double finalAverageHumidity) throws Exception{
		int response = 0;
		config = configDAO.searchConfigById(1);
		
		if(finalAverageHumidity < config.getMinimumHumidityLimit()){
			response = 1;
		}
		
		return response;
	}
	
	public List<Graph> listAllGraph(int qtdLimit) throws Exception{
		
        List<Graph> listGraph = new ArrayList<Graph>();

        Connection conexao = BDConfig.getConnection();

        String sql1 = "SELECT * FROM TEMPERATURES WHERE SERVER_ID = 1 ORDER BY ID DESC LIMIT ?";

        PreparedStatement statement1 = conexao.prepareStatement(sql1);
        statement1.setInt(1, qtdLimit);
        ResultSet rs1 = statement1.executeQuery();

        while(rs1.next()) {
        	Graph graph = new Graph();
        	graph.setTempValue(rs1.getDouble("TEMPERATURE"));
        	graph.setHumValue(rs1.getDouble("HUMIDITY"));
        	graph.setServerId(rs1.getInt("SERVER_ID"));
        	
        	Timestamp dateTime = rs1.getTimestamp("TIME");
        	String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dateTime.getTime());
        	
        	graph.setGraphDateTime(date);

        	listGraph.add(graph);
        }
        
        String sql2 = "SELECT * FROM TEMPERATURES WHERE SERVER_ID = 2 ORDER BY ID DESC LIMIT ?";

        PreparedStatement statement2 = conexao.prepareStatement(sql2);
        statement2.setInt(1, qtdLimit);
        ResultSet rs2 = statement2.executeQuery();

        while(rs2.next()) {
        	Graph graph = new Graph();
        	graph.setTempValue(rs2.getDouble("TEMPERATURE"));
        	graph.setHumValue(rs2.getDouble("HUMIDITY"));
        	graph.setServerId(rs2.getInt("SERVER_ID"));
        	
        	Timestamp dateTime = rs2.getTimestamp("TIME");
        	String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dateTime.getTime());
        	
        	graph.setGraphDateTime(date);

        	listGraph.add(graph);
        }
        
        String sql3 = "SELECT * FROM TEMPERATURES WHERE SERVER_ID = 3 ORDER BY ID DESC LIMIT ?";

        PreparedStatement statement3 = conexao.prepareStatement(sql3);
        statement3.setInt(1, qtdLimit);
        ResultSet rs3 = statement3.executeQuery();

        while(rs3.next()) {
        	Graph graph = new Graph();
        	graph.setTempValue(rs3.getDouble("TEMPERATURE"));
        	graph.setHumValue(rs3.getDouble("HUMIDITY"));
        	graph.setServerId(rs3.getInt("SERVER_ID"));
        	
        	Timestamp dateTime = rs3.getTimestamp("TIME");
        	String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dateTime.getTime());
        	
        	graph.setGraphDateTime(date);

        	listGraph.add(graph);
        }

        return listGraph;
    }
	
}
