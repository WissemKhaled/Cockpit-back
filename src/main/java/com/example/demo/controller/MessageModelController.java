package com.example.demo.controller;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.MessageModelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/MessageModel")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageModelController {

	private final MessageModelService messageModelService;

	public MessageModelController(MessageModelService messageModelService) {
		this.messageModelService = messageModelService;
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<MessageModel>> getAllMessageModelWhitStatus(@RequestParam("statusId") Integer statusId) {
		try {
			return new ResponseEntity<>(messageModelService.getAllMessageModelWhitStatus(statusId), HttpStatus.OK);

		} catch (MessageModelNotFoundException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}



	@GetMapping("/getAllMessagesById/{subcontractorId}")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelsAndStatusForSubcontractorCategory(
			@PathVariable("subcontractorId") Integer subcontractorId,
			@PageableDefault(page = 0, size = 6) Pageable pageable) {


		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelsAndStatusBySubcontractorCategory(subcontractorId);

			int start = (int) pageable.getOffset();
			int end = Math.min((start + pageable.getPageSize()), allMessages.size());
			List<MessageModel> messageModels = allMessages.subList(start, end);

			Page<MessageModel> page = new PageImpl<>(messageModels, pageable, allMessages.size());
			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return (ResponseEntity<Page<MessageModel>>) ResponseEntity.notFound();
		}
	}

}
