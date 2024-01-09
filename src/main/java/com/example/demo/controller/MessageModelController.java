package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.MessageModelService;

@RestController
@RequestMapping("/MessageModel")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageModelController {

	private final MessageModelService messageModelService;
	//private final EmailReminderSubcontractorService emailReminderSubcontractorService;



	public MessageModelController(
		MessageModelService messageModelService
	) {
		this.messageModelService = messageModelService;
	}


//	@GetMapping("/getAllMessagesById/{statusId}/{serviceProviderId}/{subcontractorId}")
	@GetMapping("/getAllMessagesById")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelsAndStatusForSubcontractorCategory(
			@RequestParam(value = "subContractorId", required = false) Integer subContractorId,
			@RequestParam(value = "serviceProviderId", required = false) Integer serviceProviderId,
			@RequestParam(value = "subContractorStatusId", required = false) Integer subContractorStatusId,
			@RequestParam(value = "serviceProviderStatusId", required = false) Integer serviceProviderStatusId,
			@PageableDefault(page = 0, size = 6) Pageable pageable) {


		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorId, serviceProviderId, subContractorStatusId, serviceProviderStatusId);

//			int start = (int) pageable.getOffset();
//			int end = Math.min((start + pageable.getPageSize()), allMessages.size());
//			List<MessageModel> messageModels = allMessages.subList(start, end);

			Page<MessageModel> page = new PageImpl<>(allMessages, pageable, allMessages.size());

//			// appel de la méthode qui gère les relances du sous-traitant
//			emailReminderSubcontractorService.checkRelaunchSubcontractor(page, subcontractorId);

			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}