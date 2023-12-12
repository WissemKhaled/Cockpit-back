package com.example.demo.service.Implementation;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import com.example.demo.service.SendMailService;

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
	public SendMail saveAndSendMail(String to, String subject, String body, String sender,  List<MultipartFile> files) throws  MessagingException {
		
		System.err.println("err");

		MimeMessage message = getMimeMessage();
		try {
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			
			helper.setPriority(1);
			helper.setFrom("jesuisuneAdressMail@test.fr");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			
			for (MultipartFile file : files) {
				
				Resource resource = new ByteArrayResource(file.getBytes());
					
				helper.addAttachment(file.getOriginalFilename(), resource);
							
			}
			
			mailSender.send(message);
			
			
		} catch (MailException | java.io.IOException e) {

			System.err.println(e.getMessage());
		}

		return null;
	}
	
	
	private MimeMessage getMimeMessage() {
		return mailSender.createMimeMessage();
	}

}
