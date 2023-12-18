package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.SendMail;

import jakarta.mail.MessagingException;

public interface SendMailService {

	SendMail saveAndSendMail(String to, String subject, String body, String sender, List<MultipartFile> file)
			throws MessagingException;

}
