package com.example.demo.service;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.SendMail;

import jakarta.mail.MessagingException;

public interface SendMailService {
	
	SendMail saveAndSendMail (SendMail mail)  throws  MessagingException;

}
