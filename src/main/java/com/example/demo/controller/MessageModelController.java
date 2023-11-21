package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.MessageModelService;
import com.example.demo.service.SubcontractorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/MessageModel")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MessageModelController {

	private final MessageModelService messageModelService;
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtServiceImplementation jwtService;
	
	
	@GetMapping("/getAll")
	public ResponseEntity<List<MessageModel>> getAllMessageModelWhitStatus(@RequestParam("statusId") Integer statusId){
		try {
			return new ResponseEntity<>(messageModelService.getAllMessageModelWhitStatus(statusId), HttpStatus.OK);
			
		} catch (MessageModelNotFoundException e) {
			
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
