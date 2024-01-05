package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.dto.mapper.GstStatusModelSubcontractorDtoMapper;
import com.example.demo.entity.GstStatusModelSubcontractor;
import com.example.demo.entity.MessageModel;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.EmailReminderMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.EmailReminderSubcontractorService;

@Service
public class EmailReminderSubcontractorServiceImpl implements EmailReminderSubcontractorService {
	
	private final EmailReminderMapper emailReminderMapper;
	private final GstStatusModelSubcontractorDtoMapper gstStatusModelSubcontractorDtoMapper;
	private final SubcontractorMapper subcontractorMapper;
	private static final Logger log = LoggerFactory.getLogger(EmailReminderSubcontractorServiceImpl.class);
	
	public EmailReminderSubcontractorServiceImpl(EmailReminderMapper emailReminderMapper, GstStatusModelSubcontractorDtoMapper gstStatusModelSubcontractorDtoMapper, SubcontractorMapper subcontractorMapper) {
		this.emailReminderMapper = emailReminderMapper;
		this.gstStatusModelSubcontractorDtoMapper = gstStatusModelSubcontractorDtoMapper;
		this.subcontractorMapper = subcontractorMapper;
	}

	/**
	 * Méthode pour convertir une date de type String vers le type LocalDateTime
	 */
	public static LocalDateTime convertStringToLocalDateTime(String dateString, String pattern) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
	    return LocalDateTime.parse(dateString, formatter);
	}
	
	/**
	 * Méthode pour mettre à jour la table intermédiaire des sous-traitants
	 * statusId queryparam non obligatoire (null quand on passe de statusId 2 à 3, quand validationDateString n'est pas null en param)
	 * validationDateString non obligatoire (null quand on passe de statusId 1 à 2) 
	 */
	@Override
	public String updateSubcontractorStatusFromInProgressToInValidation(int mmId, int statusId, int subcontractorId, String validationDateString) throws DatabaseQueryFailureException {
	    GstStatusModelSubcontractorDTO gstStatusModelSubcontractorDTO = emailReminderMapper.findAlertBySubcontractorIdAndMmId(subcontractorId, mmId);
	    
	    if (gstStatusModelSubcontractorDTO == null) {
	        log.error("Le sous-traitant avec l'ID " + subcontractorId + " n'a pas été trouvée");
	        throw new EntityNotFoundException("Le sous-traitant avec l'ID " + subcontractorId + " n'a pas été trouvée");
	    }
	    
	    gstStatusModelSubcontractorDTO.setStatusMsFkSubcontractorId(subcontractorId);
	    gstStatusModelSubcontractorDTO.setStatusMsFkMessageModelId(mmId);
	    
	    // si l'ID du status reçu du front = 1, on l'update à 2 et on update le mmId à 2 également dans la table intermédiaire et on update la date d'envoi
	    // si l'ID du status reçu du front = 2, on l'update à 3 et on update le mmId à 3 également dans la table intermédiaire et on update la date de validation
	    if (statusId == 2) {
		    gstStatusModelSubcontractorDTO.setStatusMsFkStatusId(statusId);
		    gstStatusModelSubcontractorDTO.setStatusMsSentDate(LocalDateTime.now());
		    gstStatusModelSubcontractorDTO.setStatusMsValidationDate(gstStatusModelSubcontractorDTO.getStatusMsValidationDate());		    
	    } else if (validationDateString != null) {
		    gstStatusModelSubcontractorDTO.setStatusMsFkStatusId(3);
		    gstStatusModelSubcontractorDTO.setStatusMsSentDate(gstStatusModelSubcontractorDTO.getStatusMsSentDate());
		    
		 // Conversion de la date de type string vers le type LocalDateTime avant insertion en BDD
		    String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		    LocalDateTime validationDate = convertStringToLocalDateTime(validationDateString, pattern);
	    	gstStatusModelSubcontractorDTO.setStatusMsValidationDate(validationDate);
	    }

	    GstStatusModelSubcontractor gstStatusModelSubcontractor = gstStatusModelSubcontractorDtoMapper.toGstStatusModelSubcontractor(gstStatusModelSubcontractorDTO);

	    int isGstStatusModelSubcontractorUpdated = emailReminderMapper.updateGstStatusModelSubcontractor(gstStatusModelSubcontractor);

	    if (isGstStatusModelSubcontractorUpdated == 0) {
	        log.error("Erreur de mise à jour de la table intermédiaire des sous-traitants pour le subcontractorId " + subcontractorId);
	        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table intermédiaire des sous-traitants");
	    }
	    log.info("Table intermédiaire des sous-traitants mise à jour avec succès pour le subcontractorId " + subcontractorId);
	    return "Table intermédiaire des sous-traitants mise à jour avec succès";
	}
	
	@Override
	public void checkRelaunchSubcontractor(Page<MessageModel> messageModels, int subcontractorId) {
		LocalDateTime currentDate = LocalDateTime.now();
		
		List<GstStatusModelSubcontractorDTO> gstStatusModelSubcontractorDTOList = emailReminderMapper.findSubcontractorReminderInfo(subcontractorId);
		
		for (MessageModel messageModel : messageModels) {
			// System.out.println(messageModel);
			
			if (messageModel.getMmType().contains("Relance")) {
				for (GstStatusModelSubcontractorDTO gstStatusModelSubcontractorDTO : gstStatusModelSubcontractorDTOList) {
					 // si la date de validation n'est pas null et date de 7 jours, on met à jour le status
					if (gstStatusModelSubcontractorDTO.getStatusMsValidationDate() != null && gstStatusModelSubcontractorDTO.getStatusMsValidationDate().plusDays(7).isBefore(currentDate)) {
						// on met à jour le statusId de la table intermédiaire
						gstStatusModelSubcontractorDTO.setStatusMsFkSubcontractorId(gstStatusModelSubcontractorDTO.getStatusMsFkSubcontractorId());
					    gstStatusModelSubcontractorDTO.setStatusMsFkMessageModelId(gstStatusModelSubcontractorDTO.getStatusMsFkMessageModelId());
					    gstStatusModelSubcontractorDTO.setStatusMsFkStatusId(1);
					    gstStatusModelSubcontractorDTO.setStatusMsSentDate(gstStatusModelSubcontractorDTO.getStatusMsSentDate());
					    gstStatusModelSubcontractorDTO.setStatusMsValidationDate(gstStatusModelSubcontractorDTO.getStatusMsValidationDate());
					    
					    GstStatusModelSubcontractor gstStatusModelSubcontractor = gstStatusModelSubcontractorDtoMapper.toGstStatusModelSubcontractor(gstStatusModelSubcontractorDTO);
						
						emailReminderMapper.updateGstStatusModelSubcontractor(gstStatusModelSubcontractor);
						
						log.info("Relance : Table intermédiaire mise à jour pour l'id : " + gstStatusModelSubcontractor.getStatusMsId());
					} else {
						log.error("Date de validation nulle ou < 7 jours");
					}
				}
			} else {
				System.out.println(messageModel);
				log.error("Le message model n'est pas de type relance ou son statusId n'est pas nul");
			}
		}
	}

	@Override
	public List<GstStatusModelSubcontractorDTO> getSubcontractorReminderInfoBySId(int subcontractorId) {
		Subcontractor isFoundSubcontractor = subcontractorMapper.findSubcontractorWithStatusById(subcontractorId);
		
		if (isFoundSubcontractor == null) {
			log.error("Aucun sous-traitant trouvé avec l'id " + subcontractorId);
			throw new EntityNotFoundException("Aucun sous-traitant trouvé avec l'id " + subcontractorId);
		}
		
		List<GstStatusModelSubcontractorDTO> gstStatusModelSubcontractorDTO = emailReminderMapper.findSubcontractorReminderInfo(subcontractorId);
		
		return gstStatusModelSubcontractorDTO;
	}
	
	@Override
	public GstStatusModelSubcontractorDTO getSubcontractorReminderInfoBySpIdAndMmId(int sId, int mmId) {
		GstStatusModelSubcontractorDTO gstStatusModelSubcontractorDTO = emailReminderMapper.findAlertBySubcontractorIdAndMmId(sId, mmId);
		
		if (gstStatusModelSubcontractorDTO == null) {
			log.error("Aucune info trouvé avec l'id sous-traitant " + sId + " et le message model ID " + mmId);
			throw new EntityNotFoundException("Aucune info trouvé avec l'id sous-traitant " + sId + " et le message model ID " + mmId);
		}
		
		return gstStatusModelSubcontractorDTO;
	}
}
