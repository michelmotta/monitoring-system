package com.monitoringsystem.entity;

public class Config {

	private int id;
	private Double maximumTemperatureLimit;
	private Double minimumTemperatureLimit;
	private Double maximumHumidityLimit;
	private Double minimumHumidityLimit;
	private int readingRangeTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Double getMaximumTemperatureLimit() {
		return maximumTemperatureLimit;
	}
	
	public void setMaximumTemperatureLimit(Double maximumTemperatureLimit) {
		this.maximumTemperatureLimit = maximumTemperatureLimit;
	}
	
	public Double getMinimumTemperatureLimit() {
		return minimumTemperatureLimit;
	}
	
	public void setMinimumTemperatureLimit(Double minimumTemperatureLimit) {
		this.minimumTemperatureLimit = minimumTemperatureLimit;
	}
	
	public Double getMaximumHumidityLimit() {
		return maximumHumidityLimit;
	}

	public void setMaximumHumidityLimit(Double maximumHumidityLimit) {
		this.maximumHumidityLimit = maximumHumidityLimit;
	}

	public Double getMinimumHumidityLimit() {
		return minimumHumidityLimit;
	}

	public void setMinimumHumidityLimit(Double minimumHumidityLimit) {
		this.minimumHumidityLimit = minimumHumidityLimit;
	}
	
	public int getReadingRangeTime() {
		return readingRangeTime;
	}
	
	public void setReadingRangeTime(int readingRangeTime) {
		this.readingRangeTime = readingRangeTime;
	}
}
