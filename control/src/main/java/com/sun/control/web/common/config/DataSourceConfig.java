package com.sun.control.web.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.sun.control.web.common.config.datasource.DynamicDataSource;
import com.sun.control.web.common.config.properties.DruidProperties;
import com.sun.control.web.common.entity.DsDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataSourceConfig {

    @Autowired
    private DruidProperties druidProperties;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private DataBaseDriverConfig dataBaseDriverConfig;

    @PostConstruct
    public void loadCustDataSource(){
        /*List<DsDatasource> dsDatasources = dsDatasourceMapper.selectByCondition(datasource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (DsDatasource dsDatasource : dsDatasources) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            String driverClassName = dataBaseDriverConfig.getDriver().get(dsDatasource.getDatabaseType() + "");
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(String.format(dsDatasource.getDatabaseUrl(), dsDatasource.getDatabaseIp(), dsDatasource.getDatabasePort(), dsDatasource.getDatabaseName()));
            dataSource.setUsername(dsDatasource.getDatabaseAccount());
            dataSource.setPassword(dsDatasource.getDatabasePassword());
            DataSource ds = druidProperties.dataSource(dataSource);
            targetDataSources.put(dsDatasource.getDatasourceId(), ds);
        }*/
        List<DsDatasource> dsDatasources = new ArrayList<>();
        DsDatasource dsDatasource1 = new DsDatasource();
        dsDatasource1.setDatasourceId("sunwenfei123");
        dsDatasource1.setDatabaseType(9);
        dsDatasource1.setDatabaseUrl("jdbc:mysql://localhost:3306/XIAO_QU1");
        dsDatasource1.setDatabaseAccount("root");
        dsDatasource1.setDatabasePassword("abc123");
        dsDatasources.add(dsDatasource1);
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (DsDatasource dsDatasource : dsDatasources) {
            DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
            String driverClassName = dataBaseDriverConfig.getDriver().get(dsDatasource.getDatabaseType() + "");
            dataSource.setDriverClassName(driverClassName);
            //dataSource.setUrl(String.format(dsDatasource.getDatabaseUrl(), dsDatasource.getDatabaseIp(), dsDatasource.getDatabasePort(), dsDatasource.getDatabaseName()));
            dataSource.setUrl(dsDatasource.getDatabaseUrl());
            dataSource.setUsername(dsDatasource.getDatabaseAccount());
            dataSource.setPassword(dsDatasource.getDatabasePassword());
            DataSource ds = druidProperties.dataSource(dataSource);
            targetDataSources.put(dsDatasource.getDatasourceId(), ds);
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.afterPropertiesSet();
    }
}
