package com.monitoringsystem.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.math3.util.Precision;
import org.json.JSONObject;

import com.monitoringsystem.dao.TemperatureDAO;
import com.monitoringsystem.entity.Graph;
import com.monitoringsystem.entity.Temperature;

@Path("/temperature")
public class TemperatureService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
    private TemperatureDAO temperatureDAO;

    @PostConstruct
    private void init() {
    	temperatureDAO = new TemperatureDAO();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    @Produces(MediaType.TEXT_PLAIN)
    public String addTemperature(Temperature temperature) {
        String msg = "";

        try {
            int idGenerated = temperatureDAO.addTemperature(temperature);
            msg = String.valueOf(idGenerated);

        } catch (Exception e) {
        	msg = "Error saving temperature";
            e.printStackTrace();
        }

        return msg;
    }
    
    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    @Produces(MediaType.TEXT_PLAIN)
    public String editTemperature(Temperature temperature, @PathParam("id") int idTemperature) {
        String msg = "";

        try {
        	temperatureDAO.editTemperature(temperature, idTemperature);
            msg = "Temperature edited successfully";

        } catch (Exception e) {
            msg = "Error editing temperature";
            e.printStackTrace();
        }

        return msg;
    }
    
    @GET
    @Path("/view/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Temperature searchTemperatureById(@PathParam("id") int idTemperature){
        Temperature temperature = null;

        try {
        	temperature = temperatureDAO.searchTemperatureById(idTemperature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temperature;
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteTemperature(@PathParam("id") int idTemperature) {
        String msg = "";

        try {
        	temperatureDAO.deleteTemperature(idTemperature);;
            msg = "Temperature successfully deleted";
        } catch (Exception e) {
            msg = "Error deleting temperature!";
            e.printStackTrace();
        }

        return msg;
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temperature> listTemperatures(){
        List<Temperature> listOfTemperature = null;
        try {
        	listOfTemperature = temperatureDAO.listTemperature();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return listOfTemperature;
    }
    
    @GET
    @Path("/all/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temperature> listTemperaturesWithLimit(@PathParam("limit") int qtdLimit){
        List<Temperature> listOfTemperature = null;
        try {
        	listOfTemperature = temperatureDAO.listTemperatureWithLimit(qtdLimit);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return listOfTemperature;
    }
    
    @GET
    @Path("/check/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkTemperature(@PathParam("limit") int qtdLimit){
    	JSONObject jsonResponse = new JSONObject();
    	List<Temperature> listOfTemperature = null;
        try {
        	listOfTemperature = temperatureDAO.checkTemperature(qtdLimit);
        	
        	if(listOfTemperature.size() != 0) {
        		Double averageTemperature = 0.0;
            	Double averageHumidity = 0.0;
            	
            	for(int i = 0; i < listOfTemperature.size(); i++) {
            		averageTemperature = averageTemperature + listOfTemperature.get(i).getTemperature();
            		averageHumidity = averageHumidity + listOfTemperature.get(i).getHumidity();
            	}
            	
            	Double finalAverageTemperature = averageTemperature / listOfTemperature.size();
            	Double finalAverageHumidity = averageHumidity / listOfTemperature.size();
            	
            	jsonResponse.put("average_temperature", Precision.round(finalAverageTemperature, 1));
            	jsonResponse.put("average_humidity", Precision.round(finalAverageHumidity, 1));
            	jsonResponse.put("maximum_temperature_limit_alert", temperatureDAO.checkMaximumTemperatureLimit(finalAverageTemperature));
            	jsonResponse.put("minimum_temperature_limit_alert", temperatureDAO.checkMinimumTemperatureLimit(finalAverageTemperature));
            	jsonResponse.put("maximum_humidity_limit_alert", temperatureDAO.checkMaximumHumidityLimit(finalAverageHumidity));
            	jsonResponse.put("minimum_humidity_limit_alert", temperatureDAO.checkMinimumHumidityLimit(finalAverageHumidity));
        	}
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        return jsonResponse.toString();
    }
    
    
    @GET
    @Path("/graph/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Graph> listGraph(@PathParam("limit") int qtdLimit){
    	
    	List<Graph> listTemperatureGraph = null;
    	
    	try {
        	listTemperatureGraph = temperatureDAO.listAllGraph(qtdLimit);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return listTemperatureGraph;
        
    }
}
