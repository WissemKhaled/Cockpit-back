package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.dto.GstLogResponseDTO;
import com.example.demo.dto.ResetPasswordResponseDTO;
import com.example.demo.dto.ResetPwdExpirationResponseDTO;
import com.example.demo.exception.PasswordAvailabilityException;
import com.example.demo.exception.PasswordClaimExpirationException;
import com.example.demo.service.implementation.GstLogServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/gstlogs")
public class GstLogController {
	private static final Logger log = LoggerFactory.getLogger(GstLogController.class);
	private final GstLogServiceImpl gstLogServiceImpl;

	public GstLogController(GstLogServiceImpl gstLogServiceImpl) {
		this.gstLogServiceImpl = gstLogServiceImpl;
	}

	/**
	 * Méthode qui créé et insère un log en base de donnée
	 */
	@PostMapping("/createGstLog")
	public ResponseEntity<Object> createGstLog(@Valid @RequestBody CreateGstLogDTO createGstLogDTO) {
	    try {
	        GstLogResponseDTO responseDTO = gstLogServiceImpl.saveGstLog(createGstLogDTO);

	        if ("success".equals(responseDTO.getStatus())) {
	            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception e) {
	        log.error(e.getMessage());
	        return new ResponseEntity<>(new GstLogResponseDTO("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	/**
	 * Méthode pour récupérer un log par sa valeur (son code)
	*/
	@GetMapping("/getGstLogByValue")
    public ResponseEntity<?> getGstLogByValue(@RequestParam String logValue) {
        try {
            GstLogDTO messageModelDTO = gstLogServiceImpl.getGstLogByValue(logValue);
            return new ResponseEntity<>(messageModelDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/**
     * Methode qui vérifie l'expiration de la demande de changement de mdp
     */
	@GetMapping("/checkResetPasswordExpiration")
	public ResponseEntity<ResetPwdExpirationResponseDTO> checkResetPasswordExpiration(@RequestParam String logValue) {
	    try {
	        boolean isValid = gstLogServiceImpl.checkResetPasswordExpiration(logValue);
	        ResetPwdExpirationResponseDTO response = new ResetPwdExpirationResponseDTO("success", "Operation successful");
	        response.setValid(isValid);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        log.error("IllegalArgumentException in checkResetPasswordExpiration: " + e.getMessage(), e);
	        return new ResponseEntity<>(new ResetPwdExpirationResponseDTO("error", e.getMessage()), HttpStatus.BAD_REQUEST);
	    } catch (NotFoundException e) {
	        log.error("NotFoundException in checkResetPasswordExpiration: " + e.getMessage(), e);
	        return new ResponseEntity<>(new ResetPwdExpirationResponseDTO("error", e.getMessage()), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        log.error("Unexpected Exception in checkResetPasswordExpiration: " + e.getMessage(), e);
	        return new ResponseEntity<>(new ResetPwdExpirationResponseDTO("error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/**
     * Methode qui vérifie la disponibilité d'un mot de passe
     */
	@GetMapping("/checkNewPasswordAvailability")
    public ResponseEntity<?> checkNewPasswordAvailability(@RequestParam String newPwd, @RequestParam String email) {
        try {
            boolean isAvailable = gstLogServiceImpl.checkNewPasswordAvailability(newPwd, email);
            // Return a success response if the password is available
            return new ResponseEntity<>("Le mot de passe est disponible", HttpStatus.OK);
        } catch (PasswordAvailabilityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other exceptions or log them if needed
            log.error(e.getMessage());
            return new ResponseEntity<>("Une erreur innatendue est survenue", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	 /**
     * Methode pour redéfinir le mot de passe
     */
	@PutMapping("/resetPassword")
	public ResponseEntity<ResetPasswordResponseDTO> resetUserPassword(@RequestBody Map<String, Object> requestBody) {
	    try {
	        String logValue = (String) requestBody.get("logValue");
	        String newPassword = (String) requestBody.get("newPassword");

	        gstLogServiceImpl.manageResetUserPassword(logValue, newPassword);

	        // Return a JSON response
	        ResetPasswordResponseDTO responseDTO = new ResetPasswordResponseDTO("success", "Mot de passe mis à jour avec succès");
	        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        return new ResponseEntity<>(new ResetPasswordResponseDTO("error", e.getMessage()), HttpStatus.BAD_REQUEST);
	    } catch (NotFoundException e) {
	        return new ResponseEntity<>(new ResetPasswordResponseDTO("error", e.getMessage()), HttpStatus.NOT_FOUND);
	    }	catch (PasswordClaimExpirationException e) {
	    	return new ResponseEntity<>(new ResetPasswordResponseDTO("error", e.getMessage()), HttpStatus.GONE);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new ResetPasswordResponseDTO("error", e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}

	
	/**
     * Méthode qui intercèpte les exceptions de validation
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
 		log.error(errors.toString());
 		return errors;
 	}
}
