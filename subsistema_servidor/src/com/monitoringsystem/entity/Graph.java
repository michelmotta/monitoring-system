package com.monitoringsystem.entity;

public class Graph {
	
	private String graphDateTime;
	private Double tempValue;
	private Double humValue;
	private int serverId;

	public String getGraphDateTime() {
		return graphDateTime;
	}
	
	public void setGraphDateTime(String graphDateTime) {
		this.graphDateTime = graphDateTime;
	}
	
	public Double getTempValue() {
		return tempValue;
	}
	
	public void setTempValue(Double tempValue) {
		this.tempValue = tempValue;
	}
	
	public Double getHumValue() {
		return humValue;
	}
	
	public void setHumValue(Double humValue) {
		this.humValue = humValue;
	}
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
}
