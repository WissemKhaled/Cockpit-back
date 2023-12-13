package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.SendMail;
import com.example.demo.mappers.SendMailMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SendMailServiceImpl implements SendMailService {

	private final SendMailMapper mailMapper;

	private final JavaMailSender mailSender;

	// private final ResourceLoader resourceLoader;

	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public SendMail saveAndSendMail(String to, String subject, String body, String sender, List<MultipartFile> files)
			throws MessagingException {

		MimeMessage message = getMimeMessage();
		try {

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			String signature = loadSignature();
			signature = signature.replace("[[nom]]", "et ouiassss mumu");
			System.err.println(signature);

			helper.setPriority(1);
			helper.setFrom("jesuisuneAdressMail@test.fr");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body + "<br><br>" +signature, true);
			// helper.setReplyTo(to);

			if (files != null && !files.isEmpty()) {

				for (MultipartFile file : files) {
					Resource resource = new ByteArrayResource(file.getBytes());
					helper.addAttachment(file.getOriginalFilename(), resource);
				}
			}

			
			mailSender.send(message);

		} catch (MailException | IOException e) {

			System.err.println(e.getMessage());
		}

		return null;
	}

	private MimeMessage getMimeMessage() {
		return mailSender.createMimeMessage();
	}

	public String loadSignature() throws IOException {
		String signaturePath = "signature.html";

		Resource resource = new ClassPathResource(signaturePath);

		try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		}
	}

}
