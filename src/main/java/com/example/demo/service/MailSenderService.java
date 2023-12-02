package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lombok.extern.java.Log;

@Log
@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendNewMail(String to, String subject, String body) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        
        try {
            mailSender.send(message);
            log.info("Email envoyé avec succès à " + to);
        } catch (MailException e) {
            log.severe("Erreur lors de l'envoie de l'email : " + e.getMessage());
            throw new MessagingException("Erreur lors de l'envoie de l'email", e);
        }
    }
}
