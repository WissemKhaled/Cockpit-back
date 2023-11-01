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

//	// Configuration for PostgreSQL
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
//
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

//	private final Environment environment;
//
//	// Configuration for PostgreSQL
//	@Profile("dev")
//	@Bean
//	public DataSource postgresDataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/Cockpit-app");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("1234");
//		return dataSource;
//	}
//
	@Bean
	public DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

		// Check active profiles and add scripts accordingly
		System.err.println("Executing data-test.sql");
		populator.addScript(new ClassPathResource("data-test.sql"));

		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(populator);

		return initializer;
	}
}
