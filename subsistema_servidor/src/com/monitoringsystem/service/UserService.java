package com.monitoringsystem.service;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.monitoringsystem.dao.UserDAO;
import com.monitoringsystem.entity.User;

@Path("/user")
public class UserService {

	private static final String CHARSET_UTF8 = ";charset=utf-8";
    private UserDAO userDAO;
    
    @PostConstruct
    private void init() {
    	userDAO = new UserDAO();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
  
    	JSONObject jsonResponse = new JSONObject();
    	boolean userSaved = false;

        try {
        	userSaved = userDAO.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(userSaved == true){
        	jsonResponse.put("status", "success");
        	jsonResponse.put("message", "Usuário salvo com sucesso.");
        }else {
        	jsonResponse.put("status", "error");
        	jsonResponse.put("message", "Usuário não foi salvo.");
        }
        
        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(User user, @PathParam("id") int idUser) {
    	JSONObject jsonResponse = new JSONObject();
    	boolean userSaved = false;

        try {
        	userSaved = userDAO.editUser(user, idUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(userSaved == true){
        	jsonResponse.put("status", "success");
        	jsonResponse.put("message", "Usuário salvo com sucesso.");
        }else {
        	jsonResponse.put("status", "error");
        	jsonResponse.put("message", "Usuário não foi salvo.");
        }

        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/view/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public User searchUserById(@PathParam("id") int idUser){
        User user = null;

        try {
        	user = userDAO.searchUserById(idUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@PathParam("id") int idUser) {
        String msg = "";

        try {
        	userDAO.deleteUser(idUser);
            msg = "User successfully deleted";
        } catch (Exception e) {
            msg = "Error deleting user";
            e.printStackTrace();
        }

        return msg;
    }
    
    @GET
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@QueryParam("email") String email, @QueryParam("password") String password){
    	
    	JSONObject jsonResponse = new JSONObject();
    	boolean foundUser = false;
    	
        try {
        	foundUser = userDAO.userLogin(email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(foundUser == true){
        	jsonResponse.put("status", "success");
        	jsonResponse.put("message", "Usuário encontrado.");
        }else {
        	jsonResponse.put("status", "error");
        	jsonResponse.put("message", "Usuário não encontrado");
        }

        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build(); 
    }
    
    @GET
    @Path("/details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserbyEmail(@QueryParam("email") String email){
    	
    	JSONObject jsonResponse = new JSONObject();
    	User user = new User();
    	
        try {
        	user = userDAO.findUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(user != null){
        	jsonResponse.put("status", "success");
        	jsonResponse.put("message", "Usuário encontrado.");
        	
        	JSONObject foundUser = new JSONObject();
        	foundUser.put("id", user.getId());
        	foundUser.put("username", user.getUsername());
        	foundUser.put("email", user.getEmail());
        	foundUser.put("password", user.getPassword());
        	foundUser.put("phone", user.getPhone());
        	
        	jsonResponse.put("user", foundUser);
        }else {
        	jsonResponse.put("status", "error");
        	jsonResponse.put("message", "Usuário não foi encontrado.");
        }

        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build(); 
    }
}
