package com.sts.csgits.configuration;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisMapperScannerConfiguration
 *
 * @author lizy
 * @date 2017-10-20
 */
@Configuration
@AutoConfigureAfter(MyBatisConfiguration.class)
public class MyBatisMapperScannerConfiguration {
    private static final String SQL_SESSION_FACTORY_BEAN_NAME = "sqlSessionFactory";
    private static final String MYBATIS_BASE_PACKAGE = "com.sts.csgits.dao";

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY_BEAN_NAME);
        configurer.setBasePackage(MYBATIS_BASE_PACKAGE);
        return configurer;
    }
}
