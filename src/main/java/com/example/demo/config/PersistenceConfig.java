package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.example.demo.entity.RefreshToken;
import com.example.demo.mappers.EStatusTypeHandler;

import ch.qos.logback.core.status.Status;

@Configuration
@MapperScan("com.example.demo.mappers")
public class PersistenceConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/Cockpit-app");
		dataSource.setUsername("postgres");
		dataSource.setPassword("0622178800-Yb");
		return dataSource;
	}

//	@Bean
//	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		populator.addScript(new ClassPathResource("schema.sql"));
//		populator.addScript(new ClassPathResource("data.sql"));
//
//		DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(populator);
//
//		return initializer;
//	}
//
//	// handle the enum EStatus
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.getTypeHandlerRegistry().register(Status.class,
				EStatusTypeHandler.class);
		factoryBean.setConfiguration(configuration);

		return factoryBean.getObject();
	}
}
