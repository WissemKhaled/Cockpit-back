package com.example.demo.service;

import java.io.File;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.SendMail;

import jakarta.mail.MessagingException;

public interface SendMailService {
	
	SendMail saveAndSendMail (SendMail mail, File file)  throws  MessagingException;

}
