package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import lombok.extern.java.Log;

@Configuration
public class PersistenceConfig {

	private String[] dBTypes = {"H2","postgres"};
	private String chosenDBType = dBTypes[0];

	// configuration de la BDD embarquée H2
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		if (chosenDBType.equals("H2")) {
//			// Configuration for H2 DB
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
			dataSource.setUsername("sa");
			dataSource.setPassword("");
		} else if (chosenDBType.equals("postgres")) {
//			// Configuration for PostgreSQL
			dataSource.setDriverClassName("org.postgresql.Driver");
			dataSource.setUrl("jdbc:postgresql://localhost:5432/Cockpit-app");
			dataSource.setUsername("postgres");
			dataSource.setPassword("1234");
		}
		return dataSource;
	}

	// Initialisation de postgres BDD
	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

		if (chosenDBType.equals("H2")) {
			populator.addScript(new ClassPathResource("data-test.sql"));
		} else if (chosenDBType.equals("postgres")) {
			populator.addScript(new ClassPathResource("schema-dev.sql"));
			populator.addScript(new ClassPathResource("data-dev.sql"));
		}

		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(populator);

		return initializer;
	}
}
