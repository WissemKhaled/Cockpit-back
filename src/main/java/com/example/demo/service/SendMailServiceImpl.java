package com.example.demo.service;

import java.io.File;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SendMail;
import com.example.demo.mappers.SendMailMapper;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SendMailServiceImpl implements SendMailService {

	private final SendMailMapper mailMapper;

	private final JavaMailSender mailSender;

	@Override
	public SendMail saveAndSendMail(SendMail mail, File file) throws  MessagingException {
		
		
		try {
			
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setPriority(1);
			helper.setFrom("jesuisuneAdressMail@test.fr");
			helper.setTo(mail.getMsTo());
			helper.setSubject(mail.getMsSubject());
			helper.setText(mail.getMsBody());
			
			FileSystemResource attachment = new FileSystemResource(file);
			helper.addAttachment(attachment.getFilename(), attachment);
			
			mailSender.send(message);
			
			
		} catch (MailException e) {

			System.err.println(e.getMessage());
		}

		return mail;
	}
	
	
	private MimeMessage getMimeMessage() {
		return mailSender.createMimeMessage();
	}

}
