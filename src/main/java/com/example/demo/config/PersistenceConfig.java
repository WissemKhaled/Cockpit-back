package com.example.demo.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class PersistenceConfig {

	Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);
	private String dBType = "H2";

	// configuration de la BDD embarquée H2
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		if (dBType.equals("H2")) {
//			// Configuration for H2 DB
//			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
			dataSource.setUsername("sa");
			dataSource.setPassword("");
		} else if (dBType.equals("postgres")) {
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

		if (dBType.equals("H2")) {
			logger.info("Executing data-test.sql");
			populator.addScript(new ClassPathResource("data-test.sql"));
		} else if (dBType.equals("postgres")) {
			logger.info("Executing schema-dev.sql");
			populator.addScript(new ClassPathResource("schema-dev.sql"));

			logger.info("Executing data-dev.sql");
			populator.addScript(new ClassPathResource("data-dev.sql"));
		}

		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(populator);

		return initializer;
	}

	// commented code for Spring profiles
//  Configuration for PostgreSQL
//	@Profile("dev")
//	@Bean
//	public DataSourceInitializer postresDataSourceInitializer() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/Cockpit-app");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("1234");
//		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		System.err.println("Executing schema.sql and data.sql");
//		populator.addScript(new ClassPathResource("schema.sql"));
//		populator.addScript(new ClassPathResource("data.sql"));
//		DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(populator);
//		return initializer;
//	}

//	@Profile("test")
//	@Bean
//	public DataSourceInitializer dataSourceInitializer() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		System.err.println("Executing data-test.sql");
//		populator.addScript(new ClassPathResource("data-test.sql"));
//		DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(populator);
//		return initializer;
//	}

}
