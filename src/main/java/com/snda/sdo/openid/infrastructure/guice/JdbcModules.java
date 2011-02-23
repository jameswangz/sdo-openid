package com.snda.sdo.openid.infrastructure.guice;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Driver;

public abstract class JdbcModules {

    public static DataSource dataSource(Properties properties) {
        ComboPooledDataSource datasource = new ComboPooledDataSource();
        try {
            datasource.setDriverClass(Driver.class.getName());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        datasource.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
        datasource.setUser(properties.getProperty("hibernate.connection.username"));
        datasource.setPassword(properties.getProperty("hibernate.connection.password"));
        datasource.setMinPoolSize(Integer.parseInt(properties.getProperty("jdbc.minPoolSize")));
        datasource.setMaxPoolSize(Integer.parseInt(properties.getProperty("jdbc.maxPoolSize")));
        datasource.setInitialPoolSize(Integer.parseInt(properties.getProperty("jdbc.initialPoolSize")));
        datasource.setAcquireIncrement(-1);
        datasource.setIdleConnectionTestPeriod(300);
        datasource.setBreakAfterAcquireFailure(true);
        datasource.setCheckoutTimeout(100000);

        // to disable auto-commit
        LazyConnectionDataSourceProxy proxy = new LazyConnectionDataSourceProxy(datasource);
        proxy.setDefaultAutoCommit(false);
        proxy.afterPropertiesSet();
        return proxy;
    }

}
