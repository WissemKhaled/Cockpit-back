package com.example.demo.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.SendMail;
import com.example.demo.mappers.SendMailMapper;

import io.jsonwebtoken.io.IOException;
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
	public SendMail saveAndSendMail(SendMail mail, File files) throws  MessagingException {
		

		MimeMessage message = getMimeMessage();
		try {
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			
			
				FileSystemResource attachement = new FileSystemResource(File.pathSeparator);
				helper.addAttachment(attachement.getFilename(), attachement);
				System.err.println(attachement);
				
			
			
			helper.setPriority(1);
			helper.setFrom("jesuisuneAdressMail@test.fr");
			helper.setTo(mail.getMsTo());
			helper.setSubject(mail.getMsSubject());
			helper.setText(mail.getMsBody());
			
			
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
