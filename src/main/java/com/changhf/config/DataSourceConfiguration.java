package com.changhf.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.sql.SQLException;

//@Configuration
public class DataSourceConfiguration {
    @Value("${ftcs.jdbc.driver}")
    private String driverClassName;

    @Value("${ftcs.jdbc.url}")
    private String jdbcUrl;

    @Value("${ftcs.jdbc.username}")
    private String dbUserName;

    @Value("${ftcs.jdbc.password}")
    private String dbPwd;

    @Value("${ftcs.jdbc.maxActive}")
    private Integer maxActive;

    @Value("${ftcs.jdbc.initialSize}")
    private Integer initialSize;

    @Value("${ftcs.jdbc.minIdle}")
    private Integer minIdle;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setUsername(dbUserName);
        druidDataSource.setPassword(dbPwd);
        druidDataSource.setFilters("stat");
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(3000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 'x'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws SQLException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory().getObject());
    }
}
