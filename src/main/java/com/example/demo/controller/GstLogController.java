package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.service.GstLogService;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.InactiveUserException;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.exception.PasswordAvailabilityException;
import com.example.demo.exception.PasswordClaimExpirationException;
import com.example.demo.service.implementation.GstLogServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/gstlogs")
public class GstLogController {
	private static final Logger log = LoggerFactory.getLogger(GstLogController.class);

	@Autowired(required = false)
	private GstLogServiceImpl gstLogServiceImpl;


	/**
	 * Méthode qui créé et insère un log en base de donnée
	 */
	@PostMapping("/createGstLog")
	public ResponseEntity<String> createGstLog(@Valid @RequestBody CreateGstLogDTO createGstLogDTO) {
	    try {
	        String response = gstLogServiceImpl.saveGstLog(createGstLogDTO);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } catch (NullPointerException | EntityNotFoundException | InactiveUserException | DatabaseQueryFailureException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (RuntimeException e) {
	        return new ResponseEntity<>("Une erreur inattendue s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Une erreur inattendue s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	/**
	 * Méthode pour récupérer un log par sa valeur (son code)
	*/
	@GetMapping("/getGstLogByValue")
    public ResponseEntity<?> getGstLogByValue(@RequestParam String logValue) {
        try {
            GstLogDTO gstLogDTO = gstLogServiceImpl.getGstLogByValue(logValue);
            return new ResponseEntity<>(gstLogDTO, HttpStatus.OK);
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
     * Méthode qui envoie un email de réinitialisation du mot de passe
     */
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody CreateGstLogDTO createGstLogDTO) throws InactiveUserException {
        try {
            gstLogServiceImpl.sendResetPwdLinkByEmail(createGstLogDTO);
            return new ResponseEntity<>("Email envoyé avec succès", HttpStatus.OK);
        } catch (MessagingException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MessageModelNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/**
     * Methode qui vérifie l'expiration de la demande de changement de mdp
     */
	@GetMapping("/checkResetPasswordExpiration")
	public ResponseEntity<?> checkResetPasswordExpiration(@RequestParam String logValue) {
	    try {
	        boolean isValid = gstLogServiceImpl.checkResetPasswordExpiration(logValue);
	        return new ResponseEntity<>(isValid, HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        log.error("IllegalArgumentException in checkResetPasswordExpiration: " + e.getMessage(), e);
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (NotFoundException e) {
	        log.error("NotFoundException in checkResetPasswordExpiration: " + e.getMessage(), e);
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        log.error("Unexpected Exception in checkResetPasswordExpiration: " + e.getMessage(), e);
	        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<String> resetUserPassword(@RequestBody Map<String, Object> requestBody) {
	    try {
	        String logValue = (String) requestBody.get("logValue");
	        String newPassword = (String) requestBody.get("newPassword");

	        gstLogServiceImpl.manageResetUserPassword(logValue, newPassword);

	        return new ResponseEntity<>("Mot de passe mis à jour avec succès", HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (NotFoundException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (PasswordClaimExpirationException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.GONE);
	    } catch (DatabaseQueryFailureException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
