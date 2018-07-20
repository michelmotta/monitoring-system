package com.monitoringsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.monitoringsystem.config.BDConfig;
import com.monitoringsystem.entity.Config;

public class ConfigDAO {
	
	public Config searchConfigById(int idConfig) throws Exception{
		Config config = null;

        Connection conexao = BDConfig.getConnection();

        String sql = "SELECT * FROM CONFIGS WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, idConfig);
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
        	config = new Config();
        	config.setId(rs.getInt("ID"));
        	config.setMaximumTemperatureLimit(rs.getDouble("MAXIMUM_TEMPERATURE_LIMIT"));
        	config.setMinimumTemperatureLimit(rs.getDouble("MINIMUM_TEMPERATURE_LIMIT"));
        	config.setMaximumHumidityLimit(rs.getDouble("MAXIMUM_HUMIDITY_LIMIT"));
        	config.setMinimumHumidityLimit(rs.getDouble("MINIMUM_HUMIDITY_LIMIT"));
        	config.setReadingRangeTime(rs.getInt("READING_RANGE_TIME"));
        }

        return config;
    }
	
	public boolean editConfig(Config config, int idConfig) throws Exception{
		
		boolean configSaved = false;
        Connection conexao = BDConfig.getConnection();

        String sql = "UPDATE CONFIGS SET MAXIMUM_TEMPERATURE_LIMIT = ?, MINIMUM_TEMPERATURE_LIMIT = ?, MAXIMUM_HUMIDITY_LIMIT = ?, MINIMUM_HUMIDITY_LIMIT = ?, READING_RANGE_TIME = ? WHERE ID = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setDouble(1, config.getMaximumTemperatureLimit());
        statement.setDouble(2, config.getMinimumTemperatureLimit());
        statement.setDouble(3, config.getMaximumHumidityLimit());
        statement.setDouble(4, config.getMinimumHumidityLimit());
        statement.setInt(5, config.getReadingRangeTime());
        statement.setInt(6, idConfig);
        
		int i = statement.executeUpdate();
		        
        if(i > 0){
        	configSaved = true;
        }
        
        return configSaved;
    }
}
