package com.example.demo.config;

public class PersistenceConfig {

//	// configuration de la BDD embarquée H2
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/Cockpit-app?currentSchema=schema_dev");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("root");
//		return dataSource;
//	}
//
////	 Initialisation de postgres BDD
//	@Bean
//	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		populator.addScript(new ClassPathResource("schema-dev.sql"));
//		populator.addScript(new ClassPathResource("data-dev.sql"));
//		DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(populator);
//		return initializer;
//	}

}
