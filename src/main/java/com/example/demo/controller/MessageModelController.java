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
	
	@GetMapping("/getAllMessagesByServiceProviderId/{serviceproviderId}")
	public ResponseEntity<List<MessageModel>> getAllMessageModelsAndStatusByServiceProviderId(
			@PathVariable("serviceproviderId") Integer serviceproviderId) {


		try {
			return new ResponseEntity<>(messageModelService.getAllMessageModelsAndStatusByServiceProviderId(serviceproviderId), HttpStatus.OK);

		} catch (MessageModelNotFoundException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
