package com.example.demo.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	public SendMail saveAndSendMail(SendMail mail) throws  MessagingException {

	

		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getMsRecipient()));
				mimeMessage.setFrom(new InternetAddress("jesuisuneAdressMail@test.fr"));
				mimeMessage.setText("je suis un foutu text");
				
			}
		};
		
		try {
			
			this.mailSender.send(messagePreparator);
			
		} catch (MailException e) {
			
			System.err.println(e.getMessage());
			
		}
//		mailMapper.saveMailAndSend(mail);
		
//		
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("noreply@noreply.fr");
//		message.setTo(mail.getMsRecipient());
//		message.setSubject("je suis le sujet");
//		message.setText("je suis le Text avec un FROM");
//		mailSender.send(message);

		return mail;
	}

}
