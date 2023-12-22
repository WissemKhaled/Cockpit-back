package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.service.EmailReminderServiceProviderService;
import com.example.demo.service.EmailReminderSubcontractorService;

@RestController
@RequestMapping("/emailReminder")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailReminderController {
	
	private final EmailReminderSubcontractorService emailReminderSubcontractorService;
	private final EmailReminderServiceProviderService emailReminderServiceProviderService;

	public EmailReminderController(EmailReminderSubcontractorService emailReminderService, EmailReminderServiceProviderService emailReminderServiceProviderService) {
		this.emailReminderSubcontractorService = emailReminderService;
		this.emailReminderServiceProviderService = emailReminderServiceProviderService;
	}
	
	@PutMapping("/updateAlert/subcontractor")
	public ResponseEntity<String> updateAlertSubcontractror(
			@RequestParam String validationDateString,
			@RequestParam int mmId,
			@RequestParam int subcontractorId) {
		
		try {
			String result = emailReminderSubcontractorService.updateSubcontractorStatusFromInProgressToInValidation(validationDateString, mmId, subcontractorId);
			return ResponseEntity.ok(result);
		} catch (DatabaseQueryFailureException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Echec de maj du subcontractor status: " + e.getMessage());
		}
	}
	
	@PutMapping("/updateAlert/serviceProvider")
	public ResponseEntity<String> updateAlertServiceProvider(
			@RequestParam String validationDateString,
			@RequestParam int mmId,
			@RequestParam int serviceProviderId) {
		
		try {
			String result = emailReminderServiceProviderService.updateServiceProviderStatusFromInProgressToInValidation(validationDateString, mmId, serviceProviderId);
			return ResponseEntity.ok(result);
		} catch (DatabaseQueryFailureException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Echec de maj du serviceProvider status: " + e.getMessage());
		}
	}
}
