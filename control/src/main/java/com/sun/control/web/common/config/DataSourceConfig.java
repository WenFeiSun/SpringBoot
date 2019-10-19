package com.wondersgroup.resdir.framework.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.wondersgroup.resdir.business.domain.DsDatasource;
import com.wondersgroup.resdir.business.mapper.DsDatasourceMapper;
import com.wondersgroup.resdir.framework.config.properties.DruidProperties;
import com.wondersgroup.resdir.framework.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataSourceConfig {

    @Autowired
    private DsDatasourceMapper dsDatasourceMapper;
    @Autowired
    private DruidProperties druidProperties;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private DataBaseDriverConfig dataBaseDriverConfig;

    @PostConstruct
    public void loadCustDataSource(){
        DsDatasource datasource = new DsDatasource();
        datasource.setDatasourceStatus(1);
        List<DsDatasource> dsDatasources = dsDatasourceMapper.selectByCondition(datasource);
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
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.afterPropertiesSet();
    }
}
