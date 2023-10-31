package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.UUser;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.UserInfoService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/auth") 
public class UserController {
	@Autowired
	private UserInfoService service; 

	@Autowired
	private JwtServiceImplementation jwtService;

	@Autowired
	private AuthenticationManager authenticationManager; 
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	
	@GetMapping("/user/userdetails")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public UUserDTO findByUsername(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null & authorizationHeader.startsWith("Bearer ")) {
			System.out.println(authorizationHeader);
			String token = authorizationHeader.substring(7);
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();

			return service.findUserByEmail(email);
		} else {
			return null;
		}
	}
	
	@PostMapping("/addNewUser") 
	public String addNewUser(@RequestBody UUser userInfo) {
		LOG.info("userInfo: " + userInfo.getUPassword());
		return service.addUser(userInfo); 
	}
	
	@PostMapping("/generateToken") 
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		LOG.info("generateToken function controller");
		LOG.info("Email: " + authRequest.getEmail() + " password: " + authRequest.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())); 
		
		LOG.info("test");
		System.out.println(authentication);
		if (authentication.isAuthenticated()) { 
			LOG.info("Authentication sucessfull");
			return jwtService.generateToken(authRequest.getEmail()); 
		} else {
			LOG.info("Authentication failed");
			throw new UsernameNotFoundException("invalid user request !"); 
		} 
	}
        
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
