package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.ServiceProviderNotFoundException;
import com.example.demo.mappers.ServiceProviderDtoMapper;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.ServiceProviderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/service-providers")
public class ServiceProviderController {

	private final ServiceProviderService serviceProviderService;
	private final ServiceProviderDtoMapper providerDtoMapper;

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtServiceImplementation jwtService;

	@GetMapping("/{spId}")
	public ResponseEntity<ServiceProvider> getServiceProviderById(int spId, HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		try {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring(7);
				String email = jwtService.extractUsername(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				Boolean isTokenValid = jwtService.validateToken(token, userDetails);
				if (isTokenValid) {
					serviceProviderService.getServiceProviderById(spId).stream();
					return new ResponseEntity<>(serviceProviderService.getServiceProviderById(spId), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (ServiceProviderNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
