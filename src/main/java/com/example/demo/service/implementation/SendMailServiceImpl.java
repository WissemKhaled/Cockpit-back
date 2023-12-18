package com.example.demo.service.implementation;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.SendMail;
import com.example.demo.mappers.SendMailMapper;
import com.example.demo.service.SendMailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendMailServiceImpl implements SendMailService {

	private final SendMailMapper mailMapper;

	private final JavaMailSender mailSender;

	private static final Logger LOG = getLogger(SendMailServiceImpl.class);

	public SendMailServiceImpl(SendMailMapper mailMapper, JavaMailSender mailSender) {
		this.mailMapper = mailMapper;
		this.mailSender = mailSender;
	}

	@Override
	public SendMail saveAndSendMail(String to, String subject, String body, String sender, List<MultipartFile> files)
			throws MessagingException {

		LOG.error("err");

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
			LOG.error(e.getMessage());
		}

		return null;
	}

	private MimeMessage getMimeMessage() {
		return mailSender.createMimeMessage();
	}

}
