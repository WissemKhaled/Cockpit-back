package com.example.demo.controller;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.MessageModel;
import com.example.demo.entity.SendMail;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.MessageModelService;
import com.example.demo.service.SendMailService;

import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/SendMail")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SendMailController {
	
	
	private final SendMailService mailService;
	
	@PostMapping("/saveAndSendMail")
	public ResponseEntity<?> saveAndSendMail(@Valid @RequestBody SendMail sendMail ) throws MessagingException{
		File file = new File("popo");		
		try {
			return new ResponseEntity<>(mailService.saveAndSendMail(sendMail, file), HttpStatus.OK);
			
		} catch (MessageModelNotFoundException e) {
			
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}



	
    @PostMapping("/uploadFile")
    public ResponseEntity<List<String>> handleFileUpload(@RequestParam("files") List<MultipartFile> files) {
    	System.err.println("errre");
    	SendMail sendMail =  SendMail.builder().msTo("test@tes.fr").msBody("lololo").msSubject("llllll").build();  
    	
        List<String> fileNames = files.stream()
                .map(file -> {
                	File file1 = new File(file.getOriginalFilename());
                    try {
                    	System.err.println(file1.getPath());
                        return file1.getPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Error handling file: " + file.getOriginalFilename();
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(fileNames);
    }
    
}
