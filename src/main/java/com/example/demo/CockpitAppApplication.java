package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@MapperScan("com.example.demo.mappers")
@ComponentScan(basePackages = {"com.example.demo"})
@ImportResource("classpath:spring-mybatis.xml")
public class CockpitAppApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CockpitAppApplication.class, args);
	}
}
