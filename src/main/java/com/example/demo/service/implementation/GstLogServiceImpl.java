package com.example.demo.service.implementation;

import static org.slf4j.LoggerFactory.getLogger;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.dto.GstLogResponseDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.mapper.CreateGstLogDtoMapper;
import com.example.demo.dto.mapper.GstLogDtoMapper;
import com.example.demo.entity.GstLog;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.GstLogMapper;
import com.example.demo.service.GstLogService;
import com.example.demo.service.MailSenderService;
import com.example.demo.service.UserInfoService;
import com.example.demo.utility.JsonFileLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;

@Service
public class GstLogServiceImpl implements GstLogService {

	private static final Logger LOG = getLogger(GstLogServiceImpl.class);

	private GstLogMapper gstLogMapper;

	private CreateGstLogDtoMapper createGstLogDtoMapper;

	private GstLogDtoMapper gstLogDtoMapper;

	private MailSenderService mailService;

	private UserInfoService userInfoService;

	private PasswordEncoder encoder;

	private JsonFileLoader jsonFileLoader;

	public GstLogServiceImpl(GstLogMapper gstLogMapper, CreateGstLogDtoMapper createGstLogDtoMapper,
			GstLogDtoMapper gstLogDtoMapper, MailSenderService mailService, UserInfoService userInfoService,
			PasswordEncoder encoder, JsonFileLoader jsonFileLoader) {
		this.gstLogMapper = gstLogMapper;
		this.createGstLogDtoMapper = createGstLogDtoMapper;
		this.gstLogDtoMapper = gstLogDtoMapper;
		this.mailService = mailService;
		this.userInfoService = userInfoService;
		this.encoder = encoder;
		this.jsonFileLoader = jsonFileLoader;
	}

	public GstLogResponseDTO saveGstLog(CreateGstLogDTO createGstLogDTO) {
		if (createGstLogDTO == null) {
			LOG.error("Le paramètre createGstLogDTO ne peut être null");
			return new GstLogResponseDTO("error", "Le paramètre createGstLogDTO ne peut être null");
		}

		try {
			UUserDTO user = userInfoService.findUserByEmail(createGstLogDTO.getLogEmail());
			if (user == null) {
				LOG.error("Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());
				return new GstLogResponseDTO("error",
						"Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());
			}

			GstLog gstLog = createGstLogDtoMapper.toGstLog(createGstLogDTO);
			gstLog.setLogCreationDate(LocalDateTime.now());

			int isGstLogInserted = gstLogMapper.insertLog(gstLog);
			if (isGstLogInserted == 0) {
				LOG.error("Échec de l'insertion du gst log dans la base de données");
				return new GstLogResponseDTO("error", "Échec de l'insertion du gst log dans la base de données");
			}

			// Envoi d'email avec lien vers page de réinitialisation de mdp
			sendResetPwdLinkByEmail(createGstLogDTO);

			LOG.info("gst log créé avec succès");
			return new GstLogResponseDTO("success", "gst log créé avec succès");
		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return new GstLogResponseDTO("error", "Erreur lors de la création du Gst log");
		} catch (MessagingException | UsernameNotFoundException e) {
			LOG.error("Erreur lors de l'envoi de l'email : " + e.getMessage());
			return new GstLogResponseDTO("error", "Erreur lors de l'envoi de l'email : " + e.getMessage());
		} catch (Exception e) {
			// Handle other exceptions or log them if needed
			LOG.error(e.getMessage());
			return new GstLogResponseDTO("error", e.getMessage());
		}
	}

	/**
	 * Méthode qui récupère un log par sa valeur (logValue)
	 */
	@Override
	public GstLogDTO getGstLogByValue(String logValue) throws NotFoundException {
		if (logValue != null && !logValue.isEmpty()) {
			GstLog gstLog = gstLogMapper.getLogByValue(logValue);
			if (gstLog != null) {
				GstLogDTO gstLogDTO = gstLogDtoMapper.toDto(gstLog);
				return gstLogDTO;
			} else {
				throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
			}
		} else {
			throw new IllegalArgumentException("logValue ne peut être vide ou null");
		}
	}

	@Override
	public boolean checkResetPasswordExpiration(String logValue) throws NotFoundException {
		if (logValue != null && !logValue.isEmpty()) {
			GstLog gstLog = gstLogMapper.getLogByValue(logValue);
			if (gstLog != null) {
				LocalDateTime expirationMoment = gstLog.getLogCreationDate().plusMinutes(15);
				LocalDateTime currentTime = LocalDateTime.now();

				if (currentTime.isBefore(expirationMoment)) {
					LOG.info("La demande de réinitialisation de mot de passe est encore valide");
					return true;
				} else {
					LOG.error("La demande de réinitialisation de mot de passe a expirée");
					return false;
				}
			} else {
				throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
			}
		} else {
			throw new IllegalArgumentException("logValue ne peut être vide ou null");
		}
	}

	public void sendResetPwdLinkByEmail(CreateGstLogDTO createGstLogDTO) throws MessagingException {
		try {
			String jsonContent = jsonFileLoader.loadJsonFileContent("FR.json");
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonContent);

			if (createGstLogDTO != null && jsonNode.has("messageModel")) {
				JsonNode messageModel = jsonNode.get("messageModel");
				JsonNode resetPasswordNode = messageModel.get("resetPassword");

				// Proceed to send the reset password email
				String email = createGstLogDTO.getLogEmail();
				UUserDTO user = userInfoService.findUserByEmail(email);

				if (user != null && resetPasswordNode != null) {
					String to = email;
					String subject = resetPasswordNode.get("mmSubject").asText();
					String firstName = user.getUFirstName();
					String lastName = user.getULastName();
					String resetPwdLink = "http://localhost:4200/#/renouveler_mdp?gstLogValue="
							+ createGstLogDTO.getLogValue();

					String body = resetPasswordNode.get("mmBody").asText();
					body = body.replace("[[firstName]]", firstName);
					body = body.replace("[[lastName]]", lastName);
					body = body.replace("[[resetPwdLink]]", resetPwdLink);

					mailService.sendNewMail(to, subject, body);
				} else {
					throw new UsernameNotFoundException("Utilisateur " + email + " non trouvé. Email non envoyé");
				}
			}
		} catch (IOException e) {
			LOG.error("Error reading JSON file content : " + e.toString());
		} catch (Exception e) {
			LOG.error("An unexpected error occurred : " + e.toString());
		}
	}

	public void manageResetUserPassword(String logValue, String newPassword)
			throws NotFoundException, GeneralException {
		if (logValue != null && !logValue.isEmpty()) {

			if (checkResetPasswordExpiration(logValue)) {
				// Si la demnde n'est pas expirée on redéfinit le mot de passe de l'utilisateur
				GstLog gstLog = gstLogMapper.getLogByValue(logValue);
				if (gstLog != null) {
					UUserDTO user = userInfoService.findUserByEmail(gstLog.getLogEmail());

					// décoder le mdp venant du front et encodé en base64
					byte[] decodedBytes = Base64.getDecoder().decode(newPassword);
					String decodedPwd = new String(decodedBytes);

					if (user != null) {
						userInfoService.resetPassword(encoder.encode(decodedPwd), user.getUEmail());
					} else {
						throw new UsernameNotFoundException("Utilisateur " + gstLog.getLogEmail() + " non trouvé.");
					}
				} else {
					throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
				}
			} else {
				throw new GeneralException("Demande de changement de mot de passe expirée.");
			}
		} else {
			throw new IllegalArgumentException("logValue ne peut être vide ou null");
		}
	}

}
