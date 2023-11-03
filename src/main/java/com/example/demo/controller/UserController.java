package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.dto.RefreshTokenRequestDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.RefreshToken;
import com.example.demo.mappers.RefreshTokenMapper;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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
	
	/*
	 * Méthode qui retourne les infos du user authentifié
	 * */
	@GetMapping("/user/userdetails")
	public ResponseEntity<?> findByUsername(HttpServletRequest request) {
	    String authorizationHeader = request.getHeader("Authorization");
	    
	    try {
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            String token = authorizationHeader.substring(7);
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            String email = authentication.getName();

	            UUserDTO foundUser = service.findUserByEmail(email);
	            return new ResponseEntity<>(foundUser, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	    } catch (UsernameNotFoundException e) {
	        LOG.error("User not found: {}", e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (IllegalArgumentException e) {
	        LOG.error("Invalid username: {}", e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        LOG.error("An unexpected error occurred: {}", e.getMessage(), e);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}


	
	@GetMapping("/user/test")
	public Optional<RefreshToken> findRefreshTokenWithUserByUserId(@RequestParam int uId) {
		return refreshTokenMapper.findRefreshTokenWithUserByUserId(uId);
	}
	
	/*
	 * Méthode de création d'un user avec validation du pattern de mot de passe et email
	 * */
	@PostMapping("/addNewUser") 
	public ResponseEntity<String> addNewUser(@Valid @RequestBody CreateUserDTO userInfo) {
	    LOG.info("User created with email : " + userInfo.getUEmail());
	    try {
	        String result = service.addUser(userInfo);
	        return new ResponseEntity<>(result, HttpStatus.CREATED);
	    } catch (Exception ex) {
	        LOG.error("Error creating user: " + ex.getMessage(), ex);
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	/*
	 * Méthode qui vérifie l'authentification du User et génère un token avec un tokenId servant à le refresh si l'authentification réussie
	 * */
	 @PostMapping("/generateToken")
	    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
	        
	        // On vérifie si l'utilisateur a un statut actif, si c'est le cas, on lui génère un token
	        UUserDTO user = service.findUserByEmail(authRequest.getEmail());
	        
	        if (authentication.isAuthenticated() && user.isUStatus()) {
	        	LOG.info("user authenticated");
	            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getEmail());
	            return JwtResponseDTO.builder()
	                    .accessToken(jwtService.generateToken(authRequest.getEmail()))
	                    .token(refreshToken.getRtToken()).build();
	        } else {
	        	LOG.error("invalid user request !");
	            throw new UsernameNotFoundException("invalid user request or inactive user!");
	        }
	    }
	 
	 /*
	  * Méthode qui génère un refreshToken à partir de l'id du token en cours
	  * */
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
    
	/*
	 * Méthode qui gère les exceptions d'authentification
	 * */
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    /*
     * Méthode qui gère les exceptions de validation (pattern du mdp et de l'email)
     * */
 	@ResponseStatus(HttpStatus.BAD_REQUEST)
 	@ExceptionHandler(MethodArgumentNotValidException.class)
 	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
 		HashMap<String, String> errors = new HashMap<>();
 		ex.getBindingResult().getAllErrors().forEach(e -> {
 			String fieldName = ((FieldError) e).getField();
 			String errorMessage = e.getDefaultMessage();

 			errors.put(fieldName, errorMessage);
 		});

 		return errors;
 	}
}
