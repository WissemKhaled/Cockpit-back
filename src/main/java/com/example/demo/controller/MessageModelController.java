package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.MessageModelService;
import com.example.demo.service.ModelTrackingService;

@RestController
@RequestMapping("/MessageModel")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageModelController {

	private final MessageModelService messageModelService;
	private final ModelTrackingService modelTrackingService;

	public MessageModelController(
		MessageModelService messageModelService,
		ModelTrackingService modelTrackingService
	) {
		this.messageModelService = messageModelService;
		this.modelTrackingService = modelTrackingService;
	}


	@GetMapping("/getAllMessagesById")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelsAndStatusForSubcontractorCategory(
			@RequestParam(value = "subContractorId", required = false) Integer subContractorId,
			@RequestParam(value = "serviceProviderId", required = false) Integer serviceProviderId,
			@RequestParam(value = "subContractorStatusId", required = false) Integer subContractorStatusId,
			@RequestParam(value = "serviceProviderStatusId", required = false) Integer serviceProviderStatusId,
			@PageableDefault(page = 0, size = 6) Pageable pageable) {


		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorId, serviceProviderId, subContractorStatusId, serviceProviderStatusId);

			Page<MessageModel> page = new PageImpl<>(allMessages, pageable, allMessages.size());

//			// appel de la méthode qui gère les relances selon le contractId
			// modelTrackingService.checkRelaunch(contractId);

			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/relaunch/{contractId}")
	public ResponseEntity<String> relaunch(@PathVariable("contractId") Integer contractId) {
	    try {
	        String response = modelTrackingService.checkRelaunch(contractId);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Une erreur est survenue : " + e.getMessage());
	    }
	}


}