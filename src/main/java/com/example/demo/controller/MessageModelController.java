package com.example.demo.controller;

import java.util.List;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping("/getAllMessages/{statusId}")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelWhitStatus(@PathVariable("statusId") Integer statusId, @PageableDefault(page = 0, size = 6) Pageable pageable) {
		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelWhitStatus(statusId);

			int start = (int) pageable.getOffset();
			int end = Math.min((start + pageable.getPageSize()), allMessages.size());
			List<MessageModel> messageModels = allMessages.subList(start, end);

			Page<MessageModel> page = new PageImpl<>(messageModels, pageable, allMessages.size());
			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return (ResponseEntity<Page<MessageModel>>) ResponseEntity.notFound();
		}
	}
	
	@GetMapping("/getAllMessagesByServiceProviderId/{serviceproviderId}")
	public ResponseEntity<List<MessageModel>> getAllMessageModelsAndStatusByServiceProviderId(
			@PathVariable("serviceproviderId") Integer serviceproviderId) {


		try {
			return new ResponseEntity<>(messageModelService.getAllMessageModelsAndStatusByServiceProviderId(serviceproviderId), HttpStatus.OK);

		} catch (MessageModelNotFoundException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}



	@GetMapping("/getAllMessagesBySubcontractorId/{subcontractorId}")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelsAndStatusBySubcontractorCategoryAndId(
			@PathVariable("subcontractorId") Integer subcontractorId,
			@PageableDefault(page = 0, size = 6) Pageable pageable) {


		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelsAndStatusBySubcontractorCategoryAndId(subcontractorId);

			int start = (int) pageable.getOffset();
			int end = Math.min((start + pageable.getPageSize()), allMessages.size());
			List<MessageModel> messageModels = allMessages.subList(start, end);

			Page<MessageModel> page = new PageImpl<>(messageModels, pageable, allMessages.size());
			
			// appel de la méthode qui gère les relances
			modelTrackingService.checkRelaunch(page, 1); // contractId en 2ème param
			
			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return (ResponseEntity<Page<MessageModel>>) ResponseEntity.notFound();
		}
	}

}
