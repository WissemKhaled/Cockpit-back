package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.service.GstLogServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/gstlogs") 
public class GstLogController {
	
	@Autowired
	private GstLogServiceImpl gstLogServiceImpl;
	
	/**
	 * Méthode qui créé et insère un log en base de donnée
	 */
	@PostMapping("/createGstLog")
    public ResponseEntity<String> createGstLog(@Valid @RequestBody CreateGstLogDTO createGstLogDTO) {
        try {
            String result = gstLogServiceImpl.saveGstLog(createGstLogDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.severe(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.severe(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            log.severe(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            log.severe(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.severe(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
 		log.severe(errors.toString());
 		return errors;
 	}
}
