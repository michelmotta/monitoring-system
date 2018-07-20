package com.monitoringsystem.cronjob;

import java.net.URL;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.monitoringsystem.dao.ConfigDAO;
import com.monitoringsystem.entity.Config;
import com.monitoringsystem.notification.Notification;

public class CronJobServletTemperatureChecker extends HttpServlet implements Runnable{

	private static final long serialVersionUID = 1L;
	private int rangeTime;
	private final int idConfig = 1;
	private Config config;
	private ConfigDAO configDAO;
	private Notification notification;
	private boolean notificationSent = false;

	public void init() {
		notification = new Notification();
		config = new Config();
		
		try {	
			
			configDAO = new ConfigDAO();
    		config = configDAO.searchConfigById(idConfig);
    		this.rangeTime = config.getReadingRangeTime();
    		
    		try {	
                Thread thread = new Thread(this);
                thread.start(); 
            } catch (Exception e) {
                System.out.println("Não foi possível iniciar a Thread de checagem de temperatura");  
                e.printStackTrace();
            }
            
        } catch (Exception e) {
			System.out.println("Não foi possível acessar o banco de dados - Thread de checagem de temperatura");
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
					System.out.println("Não foi possível acessar o banco de dados - Thread de checagem de temperatura");
					e.printStackTrace();
				}
        	}
        	
        	try {
            	checkTemperatureAndHumidityStatus("http://localhost:8080/monitoring-system/service/temperature/check/10");
            	
            	System.out.println("-------------------------------------------------------");
                System.out.println(" > Security Temperature Check Task Executed...");
                System.out.println("-------------------------------------------------------");
                
            	Thread.sleep(rangeTime);
            } catch (Exception e) {
                System.out.println("Não foi possível realizar requisição ao servidor - Thread de checagem de temperatura");
                e.printStackTrace();
            }
        }
    }
    
    private void checkTemperatureAndHumidityStatus(String url) throws Exception {
    	
    	String temperatureJson = IOUtils.toString(new URL(url));
		JSONObject json = new JSONObject(temperatureJson);
		
		if(json.length() != 0) {
			int maximumTemperatureLimitAlert = json.getInt("maximum_temperature_limit_alert");
			int minimumTemperatureLimitAlert = json.getInt("minimum_temperature_limit_alert");
			int maximumHumidityLimitAlert = json.getInt("maximum_humidity_limit_alert");
			int minimumHumidityLimitAlert = json.getInt("minimum_humidity_limit_alert");
			
			if(maximumTemperatureLimitAlert != 0) {
				String errorMessage = "Atenção! Temperatura do servidor acima do limite!";
				
				System.err.println("--------------------------------------------------------------------");
				System.err.println("Problema de temperatura máxima detectada! Enviando notificação...");
				System.err.println("--------------------------------------------------------------------");
				
				if(this.notificationSent == false) {
					notification.sendNotification(errorMessage);
					this.notificationSent = true;
				}
				
			}
			
			if(minimumTemperatureLimitAlert != 0) {
				String errorMessage = "Atenção! Temperatura do servidor abaixo do limite!";
				
				System.err.println("--------------------------------------------------------------------");
				System.err.println("Problema de temperatura mínima detectada! Enviando notificação...");
				System.err.println("--------------------------------------------------------------------");
				
				if(this.notificationSent == false) {
					notification.sendNotification(errorMessage);
					this.notificationSent = true;
				}
				
			}
			
			if(maximumHumidityLimitAlert != 0) {
				String errorMessage = "Atenção! umidade do servidor acima do limite!";
				
				System.err.println("--------------------------------------------------------------------");
				System.err.println("Problema de umidade máxima detectada! Enviando notificação...");
				System.err.println("--------------------------------------------------------------------");
				
				if(this.notificationSent == false) {
					notification.sendNotification(errorMessage);
					this.notificationSent = true;
				}
				
			}
			
			if(minimumHumidityLimitAlert != 0) {
				String errorMessage = "Atenção! umidade do servidor abaixo do limite!";
				
				System.err.println("--------------------------------------------------------------------");
				System.err.println("Problema de umidade mínima detectada! Enviando notificação...");
				System.err.println("--------------------------------------------------------------------");
				
				if(this.notificationSent == false) {
					notification.sendNotification(errorMessage);
					this.notificationSent = true;
				}
				
			}
		}
		
    }

}
