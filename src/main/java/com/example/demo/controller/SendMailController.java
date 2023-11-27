package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.MessageModel;
import com.example.demo.entity.SendMail;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.MessageModelService;
import com.example.demo.service.SendMailService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/SendMail")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SendMailController {
	
	
	private final SendMailService mailService;

	@PostMapping("/saveAndSendMail")
	public ResponseEntity<?> saveAndSendMail(@Valid @RequestBody SendMail sendMail ){
		try {
			return new ResponseEntity<>(mailService.saveAndSendMail(sendMail), HttpStatus.OK);
			
		} catch (MessageModelNotFoundException e) {
			
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
