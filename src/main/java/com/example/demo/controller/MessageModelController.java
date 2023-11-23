package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.GeneralException;
import com.example.demo.service.MessageModelServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/messages") 
public class MessageModelController {
	@Autowired
    private MessageModelServiceImpl messageModelService;
    
	@PostMapping("/create")
    public ResponseEntity<String> createMessageModel(@Valid @RequestBody CreateMessageModelDTO createMessageModelDTO) {
        try {
            String result = messageModelService.saveMessageModel(createMessageModelDTO);
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
