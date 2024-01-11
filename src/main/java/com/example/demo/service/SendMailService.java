package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.exception.GeneralException;

import jakarta.mail.MessagingException;

public interface SendMailService {

	String saveAndSendMail(SendMailDTO mailDTO, List<MultipartFile> file) throws MessagingException, GeneralException;

}
