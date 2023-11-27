package com.example.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SendMail;
import com.example.demo.mappers.SendMailMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SendMailServiceImpl implements SendMailService {

	private final SendMailMapper mailMapper;

	private final JavaMailSender mailSender;

	@Override
	public SendMail saveAndSendMail(SendMail mail) {

		mailMapper.saveMailAndSend(mail);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail.getMsRecipient());
		message.setSubject("je suis le sujet");
		message.setText("je suis le Text");
		mailSender.send(message);

		return mail;
	}

}
