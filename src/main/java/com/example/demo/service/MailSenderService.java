package com.example.demo.service;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class MailSenderService {

	private final JavaMailSender mailSender;
	private static final Logger LOG = getLogger(MailSenderService.class);

	public MailSenderService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendNewMail(String to, String subject, String body) throws MessagingException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);

		try {
			mailSender.send(message);
			LOG.info("Email envoyé avec succès à " + to);
		} catch (MailException e) {
			LOG.error("Erreur lors de l'envoie de l'email : " + e.getMessage());
			throw new MessagingException("Erreur lors de l'envoie de l'email", e);
		}
	}
}
