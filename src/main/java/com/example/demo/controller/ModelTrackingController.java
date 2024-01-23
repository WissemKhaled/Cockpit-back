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

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.ModelTrackingService;

@RestController
@RequestMapping("/modelTracking")
@CrossOrigin(origins = "http://localhost:4200")
public class ModelTrackingController {
	
	private final ModelTrackingService modelTrackingService;

	public ModelTrackingController(ModelTrackingService modelTrackingService) {
		this.modelTrackingService = modelTrackingService;
	}
	
	@PutMapping("/updateAlert")
	public ResponseEntity<String> updateAlertServiceProvider(
	    @RequestParam int mmId,
	    @RequestParam(required = false) Integer statusId,
	    @RequestParam int contractId,
	    @RequestParam(required = false) String validationDate
	) {
	    try {
	        int actualStatusId = (statusId != null) ? statusId.intValue() : 0;
	        String result = modelTrackingService.updateModelTrackingDemand(mmId, actualStatusId, contractId, validationDate);
	        return ResponseEntity.ok(result);
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (DatabaseQueryFailureException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de la mise à jour du modelTracking status : " + e.getMessage());
	    }
	}
	
	@GetMapping("/getAlertInfo/{contractId}")
    public ResponseEntity<?> getServiceProviderReminderInfo(@PathVariable int contractId) {
        try {
            List<ModelTrackingDTO> result = modelTrackingService.getModelTrackingInfoByContractId(contractId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	
	@GetMapping("/getAlertInfo/{contractId}/{mmId}")
    public ResponseEntity<?> getAlerInfoBySpIdAndMmId(@PathVariable int contractId, @PathVariable int mmId) {
        try {
            ModelTrackingDTO result = modelTrackingService.getModelTrackingInfoByContractIdAndMmId(contractId, mmId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	
	@PutMapping("/updateSubcontractorOrSpStatusId")
    public ResponseEntity<String> updateSubcontractororSpStatusId(
    		@RequestParam(required = false) Integer subcontractorId,
    		@RequestParam(required = false) Integer serviceProviderId
    ) throws DatabaseQueryFailureException {
        try {
            String result = modelTrackingService.updateSubcontractorOrSpStatusId(subcontractorId, serviceProviderId);
            return new ResponseEntity<>(String.format(result, subcontractorId), HttpStatus.OK);
        } catch (DatabaseQueryFailureException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NullPointerException e) {
        	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
