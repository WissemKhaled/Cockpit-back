package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GstStatusModelServiceProviderDTO;
import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.EntityNotFoundException;
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
	        @RequestParam int mmId,
	        @RequestParam(required = false) Integer statusId,
	        @RequestParam int subcontractorId,
	        @RequestParam(required = false) String validationDate
	) {
	    try {
	    	int actualStatusId = (statusId != null) ? statusId.intValue() : 0;
	        String result = emailReminderSubcontractorService.updateSubcontractorStatusFromInProgressToInValidation(mmId, actualStatusId, subcontractorId, validationDate);
	        return ResponseEntity.ok(result);
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (DatabaseQueryFailureException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de la mise à jour du subcontractor status : " + e.getMessage());
	    }
	}
	
	@GetMapping("/getAletInfo/subcontractor/{subcontractorId}")
    public ResponseEntity<?> getSubcontractorReminderInfo(@PathVariable int subcontractorId) {
        try {
        	List<GstStatusModelSubcontractorDTO> result = emailReminderSubcontractorService.getSubcontractorReminderInfoBySId(subcontractorId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	
	@GetMapping("/getAlertInfo/subcontractor/{subcontractorId}/{mmId}")
    public ResponseEntity<?> getAlerInfoBySIdAndMmId(@PathVariable int subcontractorId, @PathVariable int mmId) {
        try {
        	GstStatusModelSubcontractorDTO result = emailReminderSubcontractorService.getSubcontractorReminderInfoBySpIdAndMmId(subcontractorId, mmId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	
	@PutMapping("/updateAlert/serviceProvider")
	public ResponseEntity<String> updateAlertServiceProvider(
	    @RequestParam int mmId,
	    @RequestParam(required = false) Integer statusId,
	    @RequestParam int serviceProviderId,
	    @RequestParam(required = false) String validationDate
	) {
	    try {
	        int actualStatusId = (statusId != null) ? statusId.intValue() : 0;
	        String result = emailReminderServiceProviderService.updateServiceProviderStatusFromInProgressToInValidation(mmId, actualStatusId, serviceProviderId, validationDate);
	        return ResponseEntity.ok(result);
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (DatabaseQueryFailureException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de la mise à jour du serviceProvider status : " + e.getMessage());
	    }
	}
	
	@GetMapping("/getAlertInfo/serviceProvider/{serviceProviderId}")
    public ResponseEntity<?> getServiceProviderReminderInfo(@PathVariable int serviceProviderId) {
        try {
            List<GstStatusModelServiceProviderDTO> result = emailReminderServiceProviderService.getServiceProviderReminderInfoBySpId(serviceProviderId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	
	@GetMapping("/getAlertInfo/serviceProvider/{serviceProviderId}/{mmId}")
    public ResponseEntity<?> getAlerInfoBySpIdAndMmId(@PathVariable int serviceProviderId, @PathVariable int mmId) {
        try {
            GstStatusModelServiceProviderDTO result = emailReminderServiceProviderService.getSpReminderInfoBySpIdAndMmId(serviceProviderId, mmId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
