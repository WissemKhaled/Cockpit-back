package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class PersistenceConfig {
	/**
	 * Afin de faire fonctionner la configuration de 2 base de données (une h2 de
	 * test et une postgre de dev), modification du scope dans le pom.xlm : pour la
	 * dépendance h2 : scope test (bdd utilisée seulement pour les tests pour la
	 * dépendance postgre : scope compile (bdd utilisée pour la compilation)
	 */

	private String dBType = "postgres"; // {H2,postgres}

	// configuration de la BDD embarquée H2
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		if (dBType.equals("H2")) {
//			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
			dataSource.setUsername("sa");
			dataSource.setPassword("");
		} else if (dBType.equals("postgres")) {
//				// Configuration for PostgreSQL
			dataSource.setDriverClassName("org.postgresql.Driver");
			dataSource.setUrl("jdbc:postgresql://localhost:5432/Cockpit-app");
			dataSource.setUsername("postgres");
			dataSource.setPassword("0622178800-Yb");
		}
		return dataSource;
	}

	// Initialisation de postgres BDD
//	@Bean
//	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		if (dBType.equals("H2")) {
//			populator.addScript(new ClassPathResource("data-test.sql"));
//		} else if (dBType.equals("postgres")) {
//			populator.addScript(new ClassPathResource("schema-dev.sql"));
//			populator.addScript(new ClassPathResource("data-dev.sql"));
//		}
//
//		DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(populator);
//
//		return initializer;
//	}
}
