package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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

import com.example.demo.dto.SendMailDTO;
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


	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public String saveAndSendMail(SendMailDTO mailDTO, List<MultipartFile> files)
			throws MessagingException, GeneralException {

		MimeMessage message = getMimeMessage();
		try {

			mailDTO.setMsCreationsDate(LocalDateTime.now());
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			String signature = loadSignature();
			signature = signature.replace("[[nom]]", "et ouiassss mumu");
			//System.err.println(body);

			helper.setPriority(1);
			helper.setTo(mailDTO.getMsTo());
			helper.setSubject(mailDTO.getMsSubject());
			helper.setText(mailDTO.getMsBody() + "<br><br>" + signature, true);
		    helper.setReplyTo(mailDTO.getMsSender());

			if (files != null && !files.isEmpty()) {

				for (MultipartFile file : files) {

		                    Resource resource = new ByteArrayResource(file.getBytes());
		                    helper.addAttachment(file.getOriginalFilename(), resource);
		                } 
				}
			

			mailSender.send(message);
			mailMapper.saveMailAndSend(mailDTO);

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
