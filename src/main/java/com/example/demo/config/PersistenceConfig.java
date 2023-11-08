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

import com.example.demo.mappers.EStatusTypeHandler;

import ch.qos.logback.core.status.Status;
import lombok.extern.java.Log;

@Log
@Configuration
@MapperScan("com.example.demo.mappers")
public class PersistenceConfig {
	/**
	 * Afin de faire fonctionner la configuration de 2 base de données (une h2 de test et une postgre de dev), modification du scope dans le pom.xlm :
	 * pour la dépendance h2 : scope test (bdd utilisée seulement pour les tests
	 * pour la dépendance postgre : scope compile (bdd utilisée pour la compilation)
	*/
	
	private String dBType = "postgres";
	
	// configuration de la BDD embarquée H2
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		System.out.println("initialisation base");
		if (dBType.equals("H2")) {
//				// Configuration for H2 DB
//				return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
			dataSource.setUsername("sa");
			dataSource.setPassword("");
			System.out.println("base H2");
		} else if (dBType.equals("postgres")) {
//				// Configuration for PostgreSQL
			dataSource.setDriverClassName("org.postgresql.Driver");
			dataSource.setUrl("jdbc:postgresql://localhost:5432/Cockpit-app");
			dataSource.setUsername("postgres");
			dataSource.setPassword("postgres");
			System.out.println("initialisation postgres");
		}
		return dataSource;

	}

	// Initialisation de postgres BDD
//	@Bean
//	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		System.out.println("initialisation base");
//		if (dBType.equals("H2")) {
//			System.out.println("generate h2");
//			log.info("Executing data-test.sql");
//			populator.addScript(new ClassPathResource("data-test.sql"));
//		} else if (dBType.equals("postgres")) {
//			System.out.println("generate postgres");
//			log.info("Executing schema-dev.sql");
//			populator.addScript(new ClassPathResource("schema-dev.sql"));
//
//			log.info("Executing data-dev.sql");
//			populator.addScript(new ClassPathResource("data-dev.sql"));
//		}
//
//		DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(populator);
//
//		return initializer;
//	}


	// handle the enum EStatus
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.getTypeHandlerRegistry().register(Status.class, EStatusTypeHandler.class);
		factoryBean.setConfiguration(configuration);

		return factoryBean.getObject();
	}
}
