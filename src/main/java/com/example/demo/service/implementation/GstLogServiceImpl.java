package com.example.demo.service.implementation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.mapper.CreateGstLogDtoMapper;
import com.example.demo.dto.mapper.GstLogDtoMapper;
import com.example.demo.entity.GstLog;
import com.example.demo.entity.UUser;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.InactiveUserException;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.exception.PasswordAvailabilityException;
import com.example.demo.exception.PasswordClaimExpirationException;
import com.example.demo.mappers.GstLogMapper;
import com.example.demo.mappers.UUserMapper;
import com.example.demo.service.GstLogService;
import com.example.demo.service.MailSenderService;
import com.example.demo.service.UserInfoService;
import com.example.demo.utility.JsonFileLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;

@Service
public class GstLogServiceImpl implements GstLogService{
	private final GstLogMapper gstLogMapper;
	
	private final CreateGstLogDtoMapper createGstLogDtoMapper;
	
	private final GstLogDtoMapper gstLogDtoMapper;
	
	private final MailSenderService mailService;
	
	private final UserInfoService userInfoService;
	
	private final PasswordEncoder encoder;
	
	private final JsonFileLoader jsonFileLoader;
	
	private final UUserMapper userMapper;
	
	@Value("${reset.password.claim.expiration.duration}")
    private int ResetPasswordClaimExpirationDuration;
	
	private static final Logger log = LoggerFactory.getLogger(GstLogServiceImpl.class);
	
	public GstLogServiceImpl (
			GstLogMapper gstLogMapper,
			CreateGstLogDtoMapper createGstLogDtoMapper,
			GstLogDtoMapper gstLogDtoMapper,
			MailSenderService mailService,
			UserInfoService userInfoService,
			PasswordEncoder encoder,
			JsonFileLoader jsonFileLoader,
			UUserMapper userMapper
	) {
		this.gstLogMapper = gstLogMapper;
		this.createGstLogDtoMapper = createGstLogDtoMapper;
		this.gstLogDtoMapper = gstLogDtoMapper;
		this.mailService = mailService;
		this.userInfoService = userInfoService;
		this.encoder = encoder;
		this.jsonFileLoader = jsonFileLoader;
		this.userMapper = userMapper;
	}
	
	public String saveGstLog(CreateGstLogDTO createGstLogDTO) throws Exception {
	    if (createGstLogDTO == null) {
	        log.error("Le paramètre createGstLogDTO ne peut être null");
	        throw new NullPointerException("Le paramètre createGstLogDTO ne peut être null");
	    }

	    try {
	        UUserDTO user = userInfoService.findUserByEmail(createGstLogDTO.getLogEmail());
	        if (user == null) {
	            log.error("Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());
	            throw new EntityNotFoundException("Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());
	        }

	        if (!user.isUStatus()) {
	            log.error("L'utilisateur " + user.getUEmail() + " est inactif");
	            throw new InactiveUserException("L'utilisateur est inactif");
	        }

	        GstLog gstLog = createGstLogDtoMapper.toGstLog(createGstLogDTO);
	        gstLog.setLogCreationDate(LocalDateTime.now());

	        int isGstLogInserted = gstLogMapper.insertLog(gstLog);
	        if (isGstLogInserted == 0) {
	            log.error("Échec de l'insertion du gst log dans la base de données");
	            throw new DatabaseQueryFailureException("Échec de l'insertion du gst log dans la base de données");
	        }

	        // Envoi d'email avec lien vers page de réinitialisation de mdp
	        // sendResetPwdLinkByEmail(createGstLogDTO);

	        log.info("gst log créé avec succès");
	        return "gst log créé avec succès";
	    } catch (NullPointerException | EntityNotFoundException | InactiveUserException | DatabaseQueryFailureException e) {
	        log.error("Une exception s'est produite : ", e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Une erreur inattendue s'est produite:", e);
	        throw new RuntimeException("Une erreur inattendue s'est produite");
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
	            LocalDateTime expirationMoment = gstLog.getLogCreationDate().plusMinutes(ResetPasswordClaimExpirationDuration);
	            LocalDateTime currentTime = LocalDateTime.now();

	            if (currentTime.isBefore(expirationMoment)) {
	                log.info("La demande de réinitialisation de mot de passe est encore valide");
	                return true;
	            } else {
	                log.error("La demande de réinitialisation de mot de passe a expirée");
	                return false;
	            }
	        } else {
	            throw new NotFoundException("Aucun gst log trouvé pour le token : " + logValue);
	        }
	    } else {
	        throw new IllegalArgumentException("logValue ne peut être vide ou null");
	    }
	}
	
	public boolean checkNewPasswordAvailability(String newPwd, String email) throws PasswordAvailabilityException {
	    if (newPwd == null || newPwd.isEmpty()) {
	        throw new IllegalArgumentException("Le paramètre newPwd ne doit pas être null ou vide");
	    }

	    try {
	        List<GstLog> gstLogs = gstLogMapper.getThreeLatestLogs(email);
	        // Si la liste est vide, le password est considéré comme étant disponible
	        if (gstLogs == null || gstLogs.isEmpty()) {
	            Optional<UUser> currentUser = userMapper.findUserByEmail(email);
	            if (encoder.matches(newPwd, currentUser.get().getUPassword())) {
	                log.error("Votre nouveau mot de passe doit être différent du mot de passe actuel");
	                throw new PasswordAvailabilityException("Votre nouveau mot de passe doit être différent du mot de passe actuel");
	            }
	            return true;
	        }
	        
	        for (GstLog gstLog : gstLogs) {
	            if (encoder.matches(newPwd, gstLog.getLogPassword())) {
	                // If the password matches any of the three latest passwords, return false (not available)
	                log.error("Votre nouveau mot de passe doit être différent de vos 3 derniers");
	                throw new PasswordAvailabilityException("Votre nouveau mot de passe doit être différent de vos 3 derniers");
	            }
	        }
	        // If no match is found, return true (available)
	        return true;
	    } catch (Exception e) {
	        log.error(e.toString());
	        throw new PasswordAvailabilityException(e.getMessage().toString());
	    }
	}

	public void sendResetPwdLinkByEmail(CreateGstLogDTO createGstLogDTO) throws MessagingException, IOException {
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

	            if (user != null) {
	                if (user.isUStatus()) {
	                    if (resetPasswordNode != null) {
	                        String to = email;
	                        String subject = resetPasswordNode.get("mmSubject").asText();
	                        String firstName = user.getUFirstName();
	                        String lastName = user.getULastName();
	                        String resetPwdLink = "http://localhost:4200/#/renouveler_mdp?gstLogValue=" + createGstLogDTO.getLogValue();

	                        String body = resetPasswordNode.get("mmBody").asText();
	                        body = body.replace("[[firstName]]", firstName);
	                        body = body.replace("[[lastName]]", lastName);
	                        body = body.replace("[[resetPwdLink]]", resetPwdLink);

	                        mailService.sendNewMail(to, subject, body);
	                    } else {
	                        log.error("Problème de chargement du modèle de message. Email non envoyé");
	                        throw new MessageModelNotFoundException("Problème de chargement du modèle de message. Email non envoyé");
	                    }
	                } else {
	                    log.error("Utilisateur " + email + " inactif. Email non envoyé");
	                    throw new InactiveUserException("Utilisateur " + email + " inactif. Email non envoyé");
	                }
	            } else {
	                log.error("Utilisateur " + email + " non trouvé. Email non envoyé");
	                throw new UsernameNotFoundException("Utilisateur " + email + " non trouvé. Email non envoyé");
	            }
	        }
	    } catch (IOException e) {
	        log.error("Erreur de lecture du contenu du fichier JSON : " + e.toString());
	        throw new IOException(e.getMessage());
	    } catch (MessagingException e) {
	        log.error("Erreur lors de l'envoi de l'email : " + e.toString());
	        throw new MessagingException(e.getMessage());
	    } catch (Exception e) {
	        log.error(e.toString());
	        throw new RuntimeException(e.getMessage());
	    }
	}

	
	public void manageResetUserPassword(String logValue, String newPassword) throws NotFoundException, PasswordAvailabilityException, PasswordClaimExpirationException, DatabaseQueryFailureException, InactiveUserException {
		 if (logValue != null && !logValue.isEmpty()) {
			 
			 if (checkResetPasswordExpiration(logValue)) {
				 // Si la demnde n'est pas expirée on redéfinit le mot de passe de l'utilisateur
				GstLog gstLog = gstLogMapper.getLogByValue(logValue);
		        if (gstLog != null) {
		        	UUserDTO user = userInfoService.findUserByEmail(gstLog.getLogEmail());
		        	
		        	if (user != null) {
		        		if (user.isUStatus()) {
		        			// décoder le mdp venant du front et encodé en base64
	    			    	byte[] decodedBytes = Base64.getDecoder().decode(newPassword);
	    			    	String decodedPwd = new String(decodedBytes);
	    			    	
		        			if (this.checkNewPasswordAvailability(decodedPwd, user.getUEmail())) {
		        				userInfoService.resetPassword(encoder.encode(decodedPwd), user.getUEmail());
				        		
				        		// Maj du log pour ajouter le nouveau mdp
					        	gstLog.setLogPassword(encoder.encode(decodedPwd));
					        	gstLog.setLogLastUpdate(LocalDateTime.now());
					        	
					        	int isGstLogUpdated = gstLogMapper.updateGstLogPwd(gstLog);
					        	
					        	if (isGstLogUpdated == 0) {
					        		log.error("Échec de mise à jour du gstLog dans la base de données");
						            throw new DatabaseQueryFailureException("Échec de mise à jour du gstLog dans la base de données");
					        	}  else {
						            log.info("gstLog mis à jour pour le loogValue : " + logValue);
						        }
		        			} else {
		        				log.error("Vous avez déjà utilisé ce mot de passe, veuillez en choisir un autre");
		        				throw new PasswordAvailabilityException("Vous avez déjà utilisé ce mot de passe, veuillez en choisir un autre");
		        			}
		        			
		        		} else {
		        			log.error("L'utilisateur " + user.getUEmail() + " est inactif");
		        			throw new InactiveUserException("L'utilisateur est inactif");
		        		}
		        	} else {
		        		log.error("Utilisateur " + gstLog.getLogEmail() + " non trouvé.");
			            throw new UsernameNotFoundException("Utilisateur " + gstLog.getLogEmail() + " non trouvé.");
			        }
		        } else {
		        	log.error("Aucun gst log trouvé pour le type: " + logValue);
		            throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
		        }
			 } else {
				 log.error("Demande de changement de mot de passe expirée.");
				 throw new PasswordClaimExpirationException("Demande de changement de mot de passe expirée.");
			 }
		 } else {
			 log.error("logValue ne peut être vide ou null");
			 throw new IllegalArgumentException("logValue ne peut être vide ou null");
		 }
	}

}
