package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.exception.GeneralException;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.SendMailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/SendMail")
@CrossOrigin(origins = "http://localhost:4200")
public class SendMailController {

	private final SendMailService mailService;

	public SendMailController(SendMailService mailService) {
		this.mailService = mailService;
	}

	@PostMapping("/saveAndSendMail")
	public ResponseEntity<?> saveAndSendMail(@RequestPart("sendMail") SendMailDTO mailDTO,
			                                 @RequestPart("files") List<MultipartFile> files) throws MessagingException {
		try {
			return new ResponseEntity<>(mailService.saveAndSendMail(mailDTO, files), HttpStatus.OK);
		} catch (MessageModelNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GeneralException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
