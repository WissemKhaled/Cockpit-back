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
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.SendMail;
import com.example.demo.exception.GeneralException;
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
	public String saveAndSendMail(String to, String subject, String body, String sender, List<MultipartFile> files)
			throws MessagingException, GeneralException {

		MimeMessage message = getMimeMessage();
		try {

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			String signature = loadSignature();
			signature = signature.replace("[[nom]]", "et ouiassss mumu");
			//System.err.println(body);

			helper.setPriority(1);
			helper.setFrom("jesuisuneAdressMail@test.fr");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body + "<br><br>" + signature, true);
			// helper.setReplyTo(to);

			if (files != null && !files.isEmpty()) {

				for (MultipartFile file : files) {
					
					
		                    if (file.getSize() > DataSize.ofMegabytes(5).toBytes()) {
		                    	System.err.println("trop lourd");
		                        throw new GeneralException("La taille du fichier " + file.getOriginalFilename() + " dépasse la limite autorisée.");
		                    }

		                    Resource resource = new ByteArrayResource(file.getBytes());
		                    helper.addAttachment(file.getOriginalFilename(), resource);
		                    
		                } 
					 
				}
			

			System.err.println("errrrr");
			mailSender.send(message);

			return "le mail a ete envoyer avec succes";
		} catch (MailException | IOException e) {

		 throw new GeneralException("une erreur est survenue");
		}

		
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
