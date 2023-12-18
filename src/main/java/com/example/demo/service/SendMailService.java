package com.example.demo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.SendMail;
import com.example.demo.exception.GeneralException;

import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;

public interface SendMailService {
	
	String saveAndSendMail (SendMailDTO mailDTO,  List<MultipartFile> file)  throws  MessagingException, GeneralException ;

	 
}

