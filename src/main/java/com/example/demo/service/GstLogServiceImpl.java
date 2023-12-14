package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.dto.GstLogResponseDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.GstLog;
import com.example.demo.entity.UUser;
import com.example.demo.exception.GeneralException;
import com.example.demo.exception.PasswordAvailabilityException;
import com.example.demo.exception.PasswordClaimExpirationException;
import com.example.demo.mappers.CreateGstLogDtoMapper;
import com.example.demo.mappers.GstLogDtoMapper;
import com.example.demo.mappers.GstLogMapper;
import com.example.demo.mappers.UUserMapper;
import com.example.demo.utility.JsonFileLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import lombok.extern.java.Log;

@Log
@Service
public class GstLogServiceImpl implements GstLogService{
	@Autowired
	GstLogMapper gstLogMapper;
	
	@Autowired
	private CreateGstLogDtoMapper createGstLogDtoMapper;
	
	@Autowired 
	private GstLogDtoMapper gstLogDtoMapper;
	
	@Autowired
	private MailSenderService mailService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
    private JsonFileLoader jsonFileLoader;
	
	@Autowired
	private UUserMapper userMapper;
	
	@Value("${reset.password.claim.expiration.duration}")
    private int ResetPasswordClaimExpirationDuration;
	
	public GstLogResponseDTO saveGstLog(CreateGstLogDTO createGstLogDTO) {
	    if (createGstLogDTO == null) {
	        log.severe("Le paramètre createGstLogDTO ne peut être null");
	        return new GstLogResponseDTO("error", "Le paramètre createGstLogDTO ne peut être null");
	    }

	    try {
	        UUserDTO user = userInfoService.findUserByEmail(createGstLogDTO.getLogEmail());
	        if (user == null) {
	            log.severe("Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());
	            return new GstLogResponseDTO("error", "Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());
	        }
	        
	        if (!user.isUStatus()) {
	        	log.severe("L'utilisateur " + user.getUEmail() + " est inactif");
    			throw new GeneralException("L'utilisateur est inactif");
	        }

	        GstLog gstLog = createGstLogDtoMapper.toGstLog(createGstLogDTO);
	        gstLog.setLogCreationDate(LocalDateTime.now());

	        int isGstLogInserted = gstLogMapper.insertLog(gstLog);
	        if (isGstLogInserted == 0) {
	            log.severe("Échec de l'insertion du gst log dans la base de données");
	            return new GstLogResponseDTO("error", "Échec de l'insertion du gst log dans la base de données");
	        }

	        // Envoi d'email avec lien vers page de réinitialisation de mdp
	        sendResetPwdLinkByEmail(createGstLogDTO);

	        log.info("gst log créé avec succès");
	        return new GstLogResponseDTO("success", "gst log créé avec succès");
	    } catch (IllegalArgumentException e) {
	        log.severe(e.getMessage());
	        return new GstLogResponseDTO("error", "Erreur lors de la création du Gst log");
	    } catch (MessagingException | UsernameNotFoundException e) {
	        log.severe("Erreur lors de l'envoi de l'email : " + e.getMessage());
	        return new GstLogResponseDTO("error", "Erreur lors de l'envoi de l'email : " + e.getMessage());
	    } catch (Exception e) {
	        // Handle other exceptions or log them if needed
	        log.severe(e.getMessage());
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
	            LocalDateTime expirationMoment = gstLog.getLogCreationDate().plusMinutes(ResetPasswordClaimExpirationDuration);
	            LocalDateTime currentTime = LocalDateTime.now();

	            if (currentTime.isBefore(expirationMoment)) {
	                log.info("La demande de réinitialisation de mot de passe est encore valide");
	                return true;
	            } else {
	                log.severe("La demande de réinitialisation de mot de passe a expirée");
	                return false;
	            }
	        } else {
	            throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
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
	        log.info("liste des 3 derniers logs = " + gstLogs.toString());

	        // If the list is empty, password is considered available
	        if (gstLogs == null || gstLogs.isEmpty()) {
	            Optional<UUser> currentUser = userMapper.findByEmail(email);
	            log.info("Current user mdp = " + currentUser.get().getUPassword());
	            if (encoder.matches(newPwd, currentUser.get().getUPassword())) {
	                log.severe("Votre nouveau mot de passe doit être différent du mot de passe actuel");
	                throw new PasswordAvailabilityException("Votre nouveau mot de passe doit être différent du mot de passe actuel");
	            }
	            return true;
	        }
	        
	        for (GstLog gstLog : gstLogs) {
	            log.info("password décodé = " + newPwd + " password de la bdd = " + gstLog.getLogPassword());
	            if (encoder.matches(newPwd, gstLog.getLogPassword())) {
	                // If the password matches any of the three latest passwords, return false (not available)
	                log.severe("Votre nouveau mot de passe doit être différent de vos 3 derniers");
	                throw new PasswordAvailabilityException("Votre nouveau mot de passe doit être différent de vos 3 derniers");
	            }
	        }
	        // If no match is found, return true (available)
	        return true;
	    } catch (Exception e) {
	        log.severe(e.toString());
	        throw new PasswordAvailabilityException(e.getMessage().toString());
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

                if (user != null && user.isUStatus() && resetPasswordNode != null) {
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
                	log.severe("Utilisateur " + email + " non trouvé ou innactif. Email non envoyé");
                    throw new UsernameNotFoundException("Utilisateur " + email + " non trouvé ou innactif. Email non envoyé");
                }
            }
        } catch (IOException e) {
            log.severe("Error reading JSON file content : " + e.toString());
        } catch (Exception e) {
            log.severe("An unexpected error occurred : " +  e.toString());
        }
    }
	
	public void manageResetUserPassword(String logValue, String newPassword) throws NotFoundException, GeneralException, PasswordAvailabilityException, PasswordClaimExpirationException {
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
		        				log.info("newPassword = " + newPassword);
		    			    	
		        				userInfoService.resetPassword(encoder.encode(decodedPwd), user.getUEmail());
				        		
				        		// Maj du log pour ajouter le nouveau mdp
					        	gstLog.setLogPassword(encoder.encode(decodedPwd));
					        	gstLog.setLogLastUpdate(LocalDateTime.now());
					        	
					        	int isGstLogUpdated = gstLogMapper.updateGstLogPwd(gstLog);
					        	
					        	if (isGstLogUpdated == 0) {
					        		log.severe("Échec de mise à jour du gstLog dans la base de données");
						            throw new GeneralException("Échec de mise à jour du gstLog dans la base de données");
					        	}  else {
						            log.info("gstLog mis à jour pour le loogValue : " + logValue);
						        }
		        			} else {
		        				log.severe("Vous avez déjà utilisé ce mot de passe, veuillez en choisir un autre");
		        			}
		        			
		        		} else {
		        			log.severe("L'utilisateur " + user.getUEmail() + " est inactif");
		        			throw new GeneralException("L'utilisateur est inactif");
		        		}
		        	} else {
		        		log.severe("Utilisateur " + gstLog.getLogEmail() + " non trouvé.");
			            throw new UsernameNotFoundException("Utilisateur " + gstLog.getLogEmail() + " non trouvé.");
			        }
		        } else {
		        	log.severe("Aucun gst log trouvé pour le type: " + logValue);
		            throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
		        }
			 } else {
				 log.severe("Demande de changement de mot de passe expirée.");
				 throw new PasswordClaimExpirationException("Demande de changement de mot de passe expirée.");
			 }
		 } else {
			 log.severe("logValue ne peut être vide ou null");
			 throw new IllegalArgumentException("logValue ne peut être vide ou null");
		 }
	}

}
