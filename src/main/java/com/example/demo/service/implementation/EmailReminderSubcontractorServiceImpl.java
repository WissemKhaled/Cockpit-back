package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.dto.mapper.GstStatusModelSubcontractorDtoMapper;
import com.example.demo.entity.GstStatusModelSubcontractor;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.mappers.EmailReminderMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.EmailReminderSubcontractorService;

@Service
public class EmailReminderSubcontractorServiceImpl implements EmailReminderSubcontractorService {
	
	private final EmailReminderMapper emailReminderMapper;
	private final GstStatusModelSubcontractorDtoMapper gstStatusModelSubcontractorDtoMapper;
	private static final Logger log = LoggerFactory.getLogger(EmailReminderSubcontractorServiceImpl.class);
	
	public EmailReminderSubcontractorServiceImpl(EmailReminderMapper emailReminderMapper, GstStatusModelSubcontractorDtoMapper gstStatusModelSubcontractorDtoMapper) {
		this.emailReminderMapper = emailReminderMapper;
		this.gstStatusModelSubcontractorDtoMapper = gstStatusModelSubcontractorDtoMapper;
	}

	/**
	 * Méthode pour convertir une date de type String vers le type LocalDateTime
	 */
	public static LocalDateTime convertStringToLocalDateTime(String dateString, String pattern) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
	    return LocalDateTime.parse(dateString, formatter);
	}

	@Override
	public String updateSubcontractorStatusFromInProgressToInValidation(String validationDateString, int mmId, int subcontractorId) throws DatabaseQueryFailureException {
	    // Conversion de la date de type string vers le type LocalDateTime avant insertion en BDD
	    validationDateString = "2023-12-15T12:30:45";
	    String pattern = "yyyy-MM-dd'T'HH:mm:ss";
	    LocalDateTime validationDate = convertStringToLocalDateTime(validationDateString, pattern);

	    mmId = 2;
	    int statusId = 2; // Le status id passe de 1 à 2 comme le modèle id

	    GstStatusModelSubcontractorDTO gstStatusModelSubcontractorDTO = new GstStatusModelSubcontractorDTO();

	    gstStatusModelSubcontractorDTO.setStatusMsFkSubcontractorId(subcontractorId);
	    gstStatusModelSubcontractorDTO.setStatusMsFkMessageModelId(mmId);
	    gstStatusModelSubcontractorDTO.setStatusMsFkStatusId(statusId);
	    gstStatusModelSubcontractorDTO.setStatusMsValidationDate(validationDate);

	    GstStatusModelSubcontractor gstStatusModelSubcontractor = gstStatusModelSubcontractorDtoMapper.toGstStatusModelSubcontractor(gstStatusModelSubcontractorDTO);

	    int isGstStatusModelSubcontractorUpdated = emailReminderMapper.updateGstStatusModelSubcontractor(gstStatusModelSubcontractor);

	    if (isGstStatusModelSubcontractorUpdated == 0) {
	        log.error("Erreur de mise à jour de la table intermédiaire des sous-traitants");
	        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table intermédiaire des sous-traitants");
	    }
	    log.info("Table intermédiaire des sous-traitants mise à jour avec succès");
	    return "Table intermédiaire des sous-traitants mise à jour avec succès";
	}
}
