package com.example.demo.controller;

import java.io.File;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.SendMail;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.SendMailService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/SendMail")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SendMailController {

	//les lignes commentées sont en cours de developpement 
	//private final SendMailService mailService;

	@PostMapping("/saveAndSendMail")
	public ResponseEntity<?> saveAndSendMail(@RequestParam("files") List<MultipartFile> file,
			                                 @RequestParam("msTo") String to,
			                                 @RequestParam("msSubject") String subject,
			                                 @RequestParam("msBody") String body,
			                                 @RequestParam("msSender") String sender
			                                   ) throws MessagingException {
		
		try {
			for (MultipartFile fil : file) {
				System.err.println(fil.getOriginalFilename());
			}
	            System.out.println("Destinataire : " + to);
	            System.out.println("Sujet : " + subject);
	            System.out.println("Corps du message : " + body);
			
			return ResponseEntity.ok("Fichier envoyé avec succès.");
			//return new ResponseEntity<>(mailService.saveAndSendMail(sendMail, file), HttpStatus.OK);

		} catch (MessageModelNotFoundException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
