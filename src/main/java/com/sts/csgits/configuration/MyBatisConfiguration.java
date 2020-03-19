package com.sts.csgits.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @author lizy
 * @since
 */
@Configuration
@AutoConfigureAfter(DataSoucesConfiguration.class)
public class MyBatisConfiguration implements TransactionManagementConfigurer {
    private static final String MYBATIS_MAPPER_LOCATIONS = "classpath:mapper/*Mapper.xml";
    private static final String MYBATIS_TYPE_ALIASES_PACKAGE = "com.sts.csgits.entity";
    private static final String MYBATIS_TYPE_HANDLER_PACKAGE = "com.sts.csgits.mybatis.typehandler";
    private static final String SQL_SESSION_FACTORY_BEAN_NAME = "sqlSessionFactory";

    @Autowired
    private DataSource dataSource;
    
    @Bean(name = SQL_SESSION_FACTORY_BEAN_NAME)
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(MYBATIS_TYPE_ALIASES_PACKAGE);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MYBATIS_MAPPER_LOCATIONS));

        sqlSessionFactoryBean.setTypeHandlersPackage(MYBATIS_TYPE_HANDLER_PACKAGE);

        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
