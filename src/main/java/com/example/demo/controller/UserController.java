package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.dto.RefreshTokenRequestDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.UUser;
import com.example.demo.mappers.RefreshTokenMapper;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserInfoService;

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
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private RefreshTokenMapper refreshTokenMapper;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	
	@GetMapping("/user/userdetails")
	public UUserDTO findByUsername(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null & authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();

			return service.findUserByEmail(email);
		} else {
			return null;
		}
	}
	
	@GetMapping("/user/test")
	public Optional<RefreshToken> findRefreshTokenWithUserByUserId(@RequestParam int uId) {
		return refreshTokenMapper.findRefreshTokenWithUserByUserId(uId);
	}
	
	@PostMapping("/addNewUser") 
	public String addNewUser(@RequestBody UUser userInfo) {
		LOG.info("userInfo: " + userInfo.getUPassword());
		return service.addUser(userInfo); 
	}
	
//	@PostMapping("/generateToken") 
//	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//		LOG.info("generateToken function controller");
//		LOG.info("Email: " + authRequest.getEmail() + " password: " + authRequest.getPassword());
//		
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())); 
//		if (authentication.isAuthenticated()) { 
//			LOG.info("Authentication sucessfull");
//			return jwtService.generateToken(authRequest.getEmail()); 
//		} else {
//			LOG.info("Authentication failed");
//			throw new UsernameNotFoundException("invalid user request !"); 
//		} 
//	}
	
	 @PostMapping("/generateToken")
	    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
	        if (authentication.isAuthenticated()) {
	        	LOG.info("user authenticated");
	            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getEmail());
	            LOG.info("test yooooooooo");
	            LOG.info("refreshToken: " + refreshToken);
	            return JwtResponseDTO.builder()
	                    .accessToken(jwtService.generateToken(authRequest.getEmail()))
	                    .token(refreshToken.getRtToken()).build();
	        } else {
	            throw new UsernameNotFoundException("invalid user request !");
	        }
	    }
	
	 @PostMapping("/refreshToken")
	 public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
		 LOG.info("rtToken: " + refreshTokenRequest.getToken());
	     return refreshTokenService.findByToken(refreshTokenRequest.getToken())
	         .map(refreshTokenService::verifyExpiration)
	         .map(RefreshToken::getUUser)
	         .map(userInfo -> {
	        	    String accessToken = jwtService.generateToken(userInfo.getUEmail());

	        	    return JwtResponseDTO.builder()
	        	        .accessToken(accessToken)
	        	        .token(refreshTokenRequest.getToken())
	        	        .build();
	        	})
	         .orElseThrow(() -> new RuntimeException("Refresh token is not in the database!"));
	 }
        
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
