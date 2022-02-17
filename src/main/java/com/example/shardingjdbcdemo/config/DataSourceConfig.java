package com.example.shardingjdbcdemo.config;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.properties.ConfigurationPropertyKey;
import org.apache.shardingsphere.readwritesplitting.api.ReadwriteSplittingRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.rule.ReadwriteSplittingDataSourceRuleConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class DataSourceConfig {
    static final String MASTER = "master";
    static final String SLAVE = "slave";

    @Bean
    public JdbcTemplate jdbcTemplate() throws SQLException {
        return new JdbcTemplate(this.readWriteDataSource());
    }

    @Bean
    public DataSource readWriteDataSource() throws SQLException {
        HikariDataSource masterDataSource = new HikariDataSource();
        masterDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        masterDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test_db?useSSL=false");
        masterDataSource.setUsername("root");
        masterDataSource.setPassword("123456");

        HikariDataSource slaveDataSource = new HikariDataSource();
        slaveDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        slaveDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test_db?useSSL=false");
        slaveDataSource.setUsername("root");
        slaveDataSource.setPassword("123456");
        List<String> readDataSourceNames = Lists.newArrayList(SLAVE);
        ReadwriteSplittingDataSourceRuleConfiguration dataSourceRuleConfig = new ReadwriteSplittingDataSourceRuleConfiguration("push_admin", null, MASTER, readDataSourceNames, null);
        ReadwriteSplittingRuleConfiguration ruleConfig = new ReadwriteSplittingRuleConfiguration(Collections.singleton(dataSourceRuleConfig), new HashMap<>());
        Properties properties = new Properties();
        properties.setProperty(ConfigurationPropertyKey.SQL_SHOW.getKey(), "true");
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put(MASTER, masterDataSource);
        dataSourceMap.put(SLAVE, slaveDataSource);
        return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(ruleConfig), properties);
    }
}
