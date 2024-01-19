package com.example.demo.config;

import com.example.demo.service.JwtService;
import com.example.demo.service.implementation.JwtServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JwtAuthFilter;
import com.example.demo.service.UserInfoService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final JwtAuthFilter authFilter;

	public SecurityConfig(JwtAuthFilter authFilter) {
		this.authFilter = authFilter;
	}

	// User Creation 
	@Bean
	public UserDetailsService userDetailsService() { 
		return new UserInfoService(); 
	} 

	/**
	 * Configuration de pring security
	*/
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/auth/addNewUser", "/auth/generateToken", "/auth/refreshToken", "/gstlogs/**").permitAll())
				.authorizeHttpRequests(ar ->ar.requestMatchers("/auth/user/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/auth/admin/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/status/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/subcontractor/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/service-providers/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/modelTracking/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/messages/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/MessageModel/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/SendMail/**").authenticated())
				.authorizeHttpRequests(ar -> ar.requestMatchers("/contract/**").authenticated())
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	/**
	 * Password Encoding
	*/
	@Bean
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	} 

	@Bean
	public AuthenticationProvider authenticationProvider() { 
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
		authenticationProvider.setUserDetailsService(userDetailsService()); 
		authenticationProvider.setPasswordEncoder(passwordEncoder()); 
		return authenticationProvider; 
	} 

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
		return config.getAuthenticationManager(); 
	}

}