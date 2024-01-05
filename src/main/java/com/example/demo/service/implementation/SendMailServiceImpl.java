package com.example.demo.service.implementation;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.UUser;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.SendMailMapper;
import com.example.demo.mappers.UUserMapper;
import com.example.demo.service.SendMailService;
import com.example.demo.service.UserInfoService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendMailServiceImpl implements SendMailService {

	private final SendMailMapper mailMapper;

	private final JavaMailSender mailSender;

	private final ResourceLoader resourceLoader;
	
	private final UserInfoService infoService;
	
	private final UUserMapper userMapper;


	private static final Logger LOG = getLogger(SendMailServiceImpl.class);

	public SendMailServiceImpl(SendMailMapper mailMapper, JavaMailSender mailSender, ResourceLoader resourceLoader, UserInfoService infoService, UUserMapper userMapper) {
		this.mailMapper = mailMapper;
		this.mailSender = mailSender;
		this.resourceLoader = resourceLoader;
		this.infoService = infoService;
		this.userMapper = userMapper;
	}

	@Override
	public String saveAndSendMail(SendMailDTO mailDTO, List<MultipartFile> files)
			throws MessagingException, GeneralException {

		MimeMessage message = getMimeMessage();
		try {

			mailDTO.setMsSendDate(LocalDateTime.now());
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			// signature du mail recuperé a partir des ressources en HTML
			String signature = loadSignature();

			//on recupere les info du user qui envoie le mail pour l'inserer dans le replyTo
			Optional<UUser> user = userMapper.findById(mailDTO.getUser());
			
			helper.setPriority(1);
			helper.setTo(mailDTO.getMsTo());
			helper.setSubject(mailDTO.getMsSubject());
			helper.setText(mailDTO.getMsBody() + "<br><br>" + signature, true);
			helper.setReplyTo(user.get().getUEmail());

			if (files != null && !files.isEmpty()) {

				for (MultipartFile file : files) {

					Resource resource = new ByteArrayResource(file.getBytes());
					helper.addAttachment(file.getOriginalFilename(), resource);
				}
			}

			// ajoute des contactes en copy si il y en a
			if (mailDTO.getMsCc() != null && !mailDTO.getMsCc().isEmpty()) {

				String[] adressesCopy = mailDTO.getMsCc().split(";");

				for (String adresse : adressesCopy) {
					helper.addCc(adresse);
				}
			}
			//mailSender.send(message);
			//mailMapper.saveMailAndSend(mailDTO);

			LOG.info("Le courrier a été envoyé avec succès !");
			return "Le courrier a été envoyé avec succès !";

		} catch (MailException | IOException e) {

			LOG.error("Une erreur s'est produite lors de l'envoi du courrier. Veuillez réessayer.");
			throw new GeneralException("Une erreur s'est produite lors de l'envoi du courrier. Veuillez réessayer.");

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
