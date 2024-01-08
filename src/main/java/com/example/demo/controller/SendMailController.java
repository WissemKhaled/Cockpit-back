package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.SendMail;
import com.example.demo.entity.UUser;
import com.example.demo.exception.GeneralException;

import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.SendMailService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/SendMail")
@CrossOrigin(origins = "http://localhost:4200")
public class SendMailController {



	private final SendMailService mailService;

	public SendMailController(SendMailService mailService) {
		this.mailService = mailService;
	}

	@PostMapping("/saveAndSendMail")
	public ResponseEntity<?> saveAndSendMail(@RequestParam(value="files", required = false) List<MultipartFile> file,
			                                 @Valid @RequestBody SendMailDTO mailDTO
			                                   ) throws MessagingException {
		try {
			//SendMailDTO mailDTO = new SendMailDTO(0, "hamzaoui.h@outlook.fr", cc, subject, body, null, 1);
			
			System.err.println(mailDTO);
			return new ResponseEntity<>(mailService.saveAndSendMail(mailDTO, file), HttpStatus.OK);
		} catch (MessageModelNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GeneralException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
