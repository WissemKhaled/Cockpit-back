package com.example.demo.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.dto.RefreshTokenRequestDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.UUser;
import com.example.demo.exception.GeneralException;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/auth") 
public class UserController {
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService service; 
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtServiceImplementation jwtService;

	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	/**
	 * Méthode qui retourne les infos du user authentifié
	*/
	@GetMapping("/user/userdetails")
	public ResponseEntity<?> findUserByEmail(HttpServletRequest request) {
	    String authorizationHeader = request.getHeader("Authorization");
	    
	    try {
	    	if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            String token = authorizationHeader.substring(7);
	            // Récupère les infos de l'utilisateur à partir du token
	            String email = jwtService.extractUsername(token);
	            // Charge les détails du user en se basant sur l'email
	            UserDetails userDetails = userDetailsService.loadUserByUsername(email);            
	            // Validation du token
	            if (jwtService.validateToken(token, userDetails)) {
	            	if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	    	            UUserDTO foundUser = service.findUserByEmail(email);
	    	            return new ResponseEntity<>(foundUser, HttpStatus.OK);
	    	        } else {
	    	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	        }
	            } else {
		            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		        }
			} else {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }        
	    } catch (UsernameNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (IllegalArgumentException e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	/**
	 * Méthode de création d'un user avec validation du pattern de mot de passe et email
	*/
	@PostMapping("/addNewUser")
	public ResponseEntity<String> addNewUser(@Valid @RequestBody CreateUserDTO userInfo) {
	    try {
	        String result = service.addUser(userInfo);
	        return new ResponseEntity<>(result, HttpStatus.CREATED);
	    } catch (GeneralException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	/**
	 * Méthode qui vérifie l'authentification du User et génère un token avec un tokenId servant à le refresh si l'authentification réussie
	*/
	@PostMapping("/generateToken")
	public ResponseEntity<JwtResponseDTO> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	    try {
	    	// décoder le mdp venant du front et encodé en base64
	    	byte[] decodedBytes = Base64.getDecoder().decode(authRequest.getPassword());
	    	String decodedPwd = new String(decodedBytes);
	    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), decodedPwd));

		    // on vérifie que l'utilisateur a un status actif. Si c'est le cas, on génère un token
		    UUserDTO user = service.findUserByEmail(authRequest.getEmail());

		    if (authentication.isAuthenticated() && user.isUStatus()) {
		    	log.info("Utilisateur authentifié avec un compte actif");
		    	int userId = service.findUserByEmail(authRequest.getEmail()).getUId();
	           	 // on supprime la clé de refresh token associée à l'utilisateur s'il y en a déjà une en bdd avant d'en créer une
	           	 refreshTokenService.deleteTokenByUserId(userId);
	           	 
		        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getEmail());
		        JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
		                .accessToken(jwtService.generateToken(authRequest.getEmail()))
		                .token(refreshToken.getRtToken())
		                .build();
		        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
		    } else {
		    	log.severe("Requête utilisateur invalide ou utilisateur '" + authRequest.getEmail() + "' inactif !");
		        throw new UsernameNotFoundException("Requête utilisateur invalide ou utilisateur inactif '" + authRequest.getEmail() + "' inactif !");
		    }

	    }  catch (AuthenticationException e) {
	    	log.severe("Requête utilisateur invalide pour l'identifiant '" + authRequest.getEmail());
            throw new BadCredentialsException("Identifiants invalides"); // or a custom exception
        }
	}
	 
	 /**
	  * Méthode qui génère un refreshToken à partir de l'id du token en cours
	 */
	 @PostMapping("/refreshToken")
	 public ResponseEntity<JwtResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) throws GeneralException {
		 try {
			 Optional<RefreshToken> optionalToken = refreshTokenService.findByToken(refreshTokenRequest.getToken());

	         if (optionalToken.isPresent()) {
	             RefreshToken token = optionalToken.get();
	             refreshTokenService.verifyExpiration(token);
	             UUser userInfo = token.getUUser();

	             String accessToken = jwtService.generateToken(userInfo.getUEmail());
	             JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
	                     .accessToken(accessToken)
	                     .token(refreshTokenRequest.getToken())
	                     .build();
	             log.info("Token refreshed avec succès");
	             return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
	         } else {
	        	 log.severe("Refresh token invalide");
	             throw new GeneralException("Refresh token invalide");
	         }
	     } catch (GeneralException e) {
	    	 log.severe(e.toString());
	         throw e;
	     }
	 }
    
	/**
	 * Méthode qui gère les exceptions d'authentification
	*/
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    /** 
     * Méthode qui intercèpte les exceptions du type GeneralException
    */
 	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<String> handleGeneralException(GeneralException ex) {
 		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
 	}
    
    /**
     * Méthode qui intercèpte les exceptions de validation (pattern du mdp et de l'email...)
    */
 	@ResponseStatus(HttpStatus.BAD_REQUEST)
 	@ExceptionHandler(MethodArgumentNotValidException.class)
 	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
 		HashMap<String, String> errors = new HashMap<>();
 		ex.getBindingResult().getAllErrors().forEach(e -> {
 			String fieldName = ((FieldError) e).getField();
 			String errorMessage = e.getDefaultMessage();

 			errors.put(fieldName, errorMessage);
 		});
 		log.severe(errors.toString());
 		return errors;
 	}
}
