package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class CockpitAppApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CockpitAppApplication.class, args);
	}
}
