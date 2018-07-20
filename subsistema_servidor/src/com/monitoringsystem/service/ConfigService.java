package com.monitoringsystem.service;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.monitoringsystem.dao.ConfigDAO;
import com.monitoringsystem.entity.Config;

@Path("/config")
public class ConfigService {

	private static final String CHARSET_UTF8 = ";charset=utf-8";
    private ConfigDAO configDAO;
    
    @PostConstruct
    private void init() {
    	configDAO = new ConfigDAO();
    }
    
    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editConfig(Config config) {
    	
    	JSONObject jsonResponse = new JSONObject();
    	boolean configSaved = false;


        try {
        	configSaved = configDAO.editConfig(config, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(configSaved == true){
        	jsonResponse.put("status", "success");
        	jsonResponse.put("message", "Configuração salva com sucesso.");
        }else {
        	jsonResponse.put("status", "error");
        	jsonResponse.put("message", "Configuração não foi salva.");
        }

        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchConfigById(){
    	
    	JSONObject jsonResponse = new JSONObject();
        Config config = null;

        try {
        	config = configDAO.searchConfigById(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(config != null){
        	jsonResponse.put("status", "success");
        	jsonResponse.put("message", "Configuração encontrada.");
        	
        	JSONObject foundConfig = new JSONObject();
        	foundConfig.put("id", config.getId());
        	foundConfig.put("maxTemp", config.getMaximumTemperatureLimit());
        	foundConfig.put("minTemp", config.getMinimumTemperatureLimit());
        	foundConfig.put("maxHum", config.getMaximumHumidityLimit());
        	foundConfig.put("minHum", config.getMinimumHumidityLimit());
        	foundConfig.put("range", config.getReadingRangeTime());
        	
        	jsonResponse.put("config", foundConfig);
        	
        }else {
        	jsonResponse.put("status", "error");
        	jsonResponse.put("message", "Configuração não encontrada.");
        }

        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build(); 
    }
}
