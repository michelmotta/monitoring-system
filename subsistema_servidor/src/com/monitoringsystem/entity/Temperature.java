package com.monitoringsystem.entity;

public class Temperature {
	
	private int id;
	private int server_id;
    private String server_name;
    private double temperature;
    private double humidity;
    private String time;
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getServer_id() {
		return server_id;
	}
	
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	
	public String getServer_name() {
		return server_name;
	}
	
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public double getHumidity() {
		return humidity;
	}
	
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
}
