package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity<?> saveAndSendMail(@RequestParam("files") List<MultipartFile> file,
			@RequestParam("msTo") String to, @RequestParam("msSubject") String subject,
			@RequestParam("msBody") String body, @RequestParam("msSender") String sender) throws MessagingException {

		try {
			for (MultipartFile fil : file) {
				System.err.println(fil.getOriginalFilename());
			}
			System.out.println("Destinataire : " + to);
			System.out.println("Sujet : " + subject);
			System.out.println("Corps du message : " + body);

			return new ResponseEntity<>(mailService.saveAndSendMail(to, subject, body, sender, file), HttpStatus.OK);

		} catch (MessageModelNotFoundException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
