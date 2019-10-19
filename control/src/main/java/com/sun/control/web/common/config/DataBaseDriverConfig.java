package com.wondersgroup.resdir.framework.datasource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataBaseDriverConfig {

    @Value("${dataBaseDriver.driver1}")
    private String driver1;
    @Value("${dataBaseDriver.driver2}")
    private String driver2;
    @Value("${dataBaseDriver.driver3}")
    private String driver3;
    @Value("${dataBaseDriver.driver4}")
    private String driver4;
    @Value("${dataBaseDriver.driver5}")
    private String driver5;
    @Value("${dataBaseDriver.driver6}")
    private String driver6;
    @Value("${dataBaseDriver.driver7}")
    private String driver7;
    @Value("${dataBaseDriver.driver8}")
    private String driver8;
    @Value("${dataBaseDriver.driver9}")
    private String driver9;
    @Value("${dataBaseDriver.driver10}")
    private String driver10;
    @Value("${dataBaseDriver.driver11}")
    private String driver11;

    Map<String, String> driverMap = new HashMap<String,String>();

    @Bean
    public Map<String, String> getDriver(){
        driverMap.put("1", driver1);
        driverMap.put("2", driver2);
        driverMap.put("3", driver3);
        driverMap.put("4", driver4);
        driverMap.put("5", driver5);
        driverMap.put("6", driver6);
        driverMap.put("7", driver7);
        driverMap.put("8", driver8);
        driverMap.put("9", driver9);
        driverMap.put("10", driver10);
        driverMap.put("11", driver11);
        return driverMap;
    }
}
