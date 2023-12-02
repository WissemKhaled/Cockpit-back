package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.dto.MessageModelDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.GstLog;
import com.example.demo.entity.MessageModel;
import com.example.demo.entity.UUser;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.CreateGstLogDtoMapper;
import com.example.demo.mappers.GstLogDtoMapper;
import com.example.demo.mappers.GstLogMapper;
import com.example.demo.mappers.MessageModelDtoMapper;
import com.example.demo.mappers.MessageModelMapper;

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
	MessageModelMapper messageModelMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public String saveGstLog(CreateGstLogDTO createGstLogDTO) throws GeneralException {
	    if (createGstLogDTO == null) {
	        log.severe("Le paramètre createGstLogDTO ne peut être null");
	        throw new IllegalArgumentException("Le paramètre createGstLogDTO ne peut être null");
	    }

	    try {
	        UUserDTO user = userInfoService.findUserByEmail(createGstLogDTO.getLogEmail());
	        if (user == null) {
	            log.severe("Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());
	            return "Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail();
	        }

	        GstLog gstLog = createGstLogDtoMapper.toGstLog(createGstLogDTO);
	        gstLog.setLogCreationDate(LocalDateTime.now());

	        int isGstLogInserted = gstLogMapper.insertLog(gstLog);
	        if (isGstLogInserted == 0) {
	            log.severe("Échec de l'insertion du gst log dans la base de données");
	            throw new GeneralException("Échec de l'insertion du gst log dans la base de données");
	        }

	        // Envoi d'email avec lien vers page de réinitialisation de mdp
	        sendResetPwdLinkByEmail(createGstLogDTO);

	        log.info("gst log créé avec succès");
	        return "gst log créé avec succès";
	    } catch (IllegalArgumentException | GeneralException e) {
	        log.severe(e.getMessage());
	        throw new GeneralException("Erreur lors de la création du Gst log");
	    } catch (MessagingException | UsernameNotFoundException e) {
	        log.severe("Erreur lors de l'envoi de l'email : " + e.getMessage());
	        // Handle specific exception, if needed
	        return "Erreur lors de l'envoi de l'email : " + e.getMessage();
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
	             GstLogDTO messageModelDTO = gstLogDtoMapper.toDto(gstLog);
	             return messageModelDTO;
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

	public void sendResetPwdLinkByEmail(CreateGstLogDTO createGstLogDTO) throws MessagingException {
	    List<MessageModel> messageModelDto = messageModelMapper.getMessageModelByType("RESET_PASSWORD");

	    if (createGstLogDTO != null && !messageModelDto.isEmpty()) {
	        String email = createGstLogDTO.getLogEmail();
	        UUserDTO user = userInfoService.findUserByEmail(email);

	        if (user != null) {
	            // L'utilisa, proceed to send the reset password email
	            String to = email;
	            String subject = messageModelDto.get(0).getMmSubject();
	            String firstName = user.getUFirstName();
	            String lastName = user.getULastName();
	            String resetPwdLink = "http://localhost:4200/renouveler_mdp?gstLogValue=" + createGstLogDTO.getLogValue();

	            String body = messageModelDto.get(0).getMmBody();
	            body = body.replace("[[firstName]]", firstName);
	            body = body.replace("[[lastName]]", lastName);
	            body = body.replace("[[resetPwdLink]]", resetPwdLink);

	            mailService.sendNewMail(to, subject, body);
	        } else {
	            throw new UsernameNotFoundException("Utilisateur " + email + " non trouvé. Email non envoyé");
	        }
	    }
	}
	
	public void manageResetUserPassword(String logValue, String newPassword) throws NotFoundException {
		 if (logValue != null && !logValue.isEmpty()) {
			 
			 if (checkResetPasswordExpiration(logValue)) {
				 // Si la demnde n'est pas expirée on redéfinit le mot de passe de l'utilisateur
				GstLog gstLog = gstLogMapper.getLogByValue(logValue);
		        if (gstLog != null) {
		        	UUserDTO user = userInfoService.findUserByEmail(gstLog.getLogEmail());
		        	
		        	if (user != null) {
		        		userInfoService.resetPassword(encoder.encode(newPassword), user.getUEmail());
		        	} else {
			            throw new UsernameNotFoundException("Utilisateur " + gstLog.getLogEmail() + " non trouvé.");
			        }
		        } else {
		            throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
		        }
			 }
		 } else {
			 throw new IllegalArgumentException("logValue ne peut être vide ou null");
		 }
	}

}
