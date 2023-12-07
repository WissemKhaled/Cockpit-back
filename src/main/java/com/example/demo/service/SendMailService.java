package com.example.demo.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.SendMail;

import jakarta.mail.MessagingException;

public interface SendMailService {
	
	SendMail saveAndSendMail (SendMail mail, List<MultipartFile> files)  throws  MessagingException;

}
