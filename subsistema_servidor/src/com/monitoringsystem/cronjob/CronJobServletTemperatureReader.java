package com.monitoringsystem.cronjob;

import java.net.URL;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.monitoringsystem.dao.ConfigDAO;
import com.monitoringsystem.dao.TemperatureDAO;
import com.monitoringsystem.entity.Config;
import com.monitoringsystem.entity.Temperature;

public class CronJobServletTemperatureReader extends HttpServlet implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private int rangeTime;
	private final int idConfig = 1;
	Config config;
	ConfigDAO configDAO;

    public void init() {
    	
    	config = new Config();
    	
    	try {
    		
    		configDAO = new ConfigDAO();
    		config = configDAO.searchConfigById(idConfig);
    		this.rangeTime = config.getReadingRangeTime();
    		
    		try {	
                Thread thread = new Thread(this);
                thread.start(); 
            } catch (Exception e) {
                System.out.println("Não foi possível iniciar a Thread de leitura de temperatura");  
                e.printStackTrace();
            }
    		
		} catch (Exception e) {
			System.out.println("Não foi possível acessar o banco de dados - Thread de leitura de temperatura");
			e.printStackTrace();
		}
    }

    @Override
    public void run() {
    	
    	long currentMillisStart = Calendar.getInstance().getTimeInMillis();
    	
        while (true) {
        	if(currentMillisStart < currentMillisStart + rangeTime) {
        		try {
        			configDAO = new ConfigDAO();
            		config = configDAO.searchConfigById(idConfig);
            		this.rangeTime = config.getReadingRangeTime();
            		currentMillisStart = Calendar.getInstance().getTimeInMillis();
				} catch (Exception e) {
					System.out.println("Não foi possível acessar o banco de dados - Thread de leitura de temperatura");
					e.printStackTrace();
				}
        	}
        	
            try {
            	
            	sendGetTemperature("http://10.87.9.180/api/sensor");
            	sendGetTemperature("http://10.87.9.181/api/sensor");
            	sendGetTemperature("http://10.87.9.182/api/sensor");
            	
            	System.out.println("-------------------------------------------------------");
                System.out.println(" > Temperature Reading Task Executed...");
                System.out.println("-------------------------------------------------------");

                Thread.sleep(rangeTime);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Não foi possível realizar requisição ao servidor - Thread de leitura de temperatura");
            }
        }
    }
    
    private void sendGetTemperature(String url) throws Exception {
    	
    	java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
    	
    	String temperatureJson = IOUtils.toString(new URL(url));
		JSONObject json = new JSONObject(temperatureJson);
		
		if(json.has("server_id") && json.has("server_name") && json.has("temperature") && json.has("humidity")) {
			Temperature temperature = new Temperature();
			
			temperature.setServer_id(json.getInt("server_id"));
			temperature.setServer_name(json.getString("server_name"));
			temperature.setTemperature(json.getDouble("temperature"));
			temperature.setHumidity(json.getDouble("humidity"));
			temperature.setTime(currentTime);
			
			System.out.println("Temperatura: " + temperature.getTemperature() + " - Humidade: " + temperature.getHumidity());
			
			TemperatureDAO temperatureDAO = new TemperatureDAO();
			temperatureDAO.addTemperature(temperature);
		}
		
	}
}
