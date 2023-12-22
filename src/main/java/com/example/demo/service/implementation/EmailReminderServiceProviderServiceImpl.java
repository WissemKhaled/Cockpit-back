package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GstStatusModelServiceProviderDTO;
import com.example.demo.dto.mapper.GstStatusModelServiceProviderDtoMapper;
import com.example.demo.entity.GstStatusModelServiceProvider;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.mappers.EmailReminderMapper;
import com.example.demo.service.EmailReminderServiceProviderService;

@Service
public class EmailReminderServiceProviderServiceImpl implements EmailReminderServiceProviderService {
	
	private final EmailReminderMapper emailReminderMapper;
	private final GstStatusModelServiceProviderDtoMapper gstStatusModelServiceProviderDtoMapper;
	private static final Logger log = LoggerFactory.getLogger(EmailReminderServiceProviderServiceImpl.class);
	
	public EmailReminderServiceProviderServiceImpl(EmailReminderMapper emailReminderMapper,
			GstStatusModelServiceProviderDtoMapper gstStatusModelServiceProviderDtoMapper) {
		this.emailReminderMapper = emailReminderMapper;
		this.gstStatusModelServiceProviderDtoMapper = gstStatusModelServiceProviderDtoMapper;
	}

	/**
	 * Méthode pour convertir une date de type String vers le type LocalDateTime
	 */
	public static LocalDateTime convertStringToLocalDateTime(String dateString, String pattern) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
	    return LocalDateTime.parse(dateString, formatter);
	}
	
	@Override
	public String updateServiceProviderStatusFromInProgressToInValidation(String validationDateString, int mmId, int serviceProviderId) throws DatabaseQueryFailureException {
		// Conversion de la date de type string vers le type LocalDateTime avant insertion en BDD
	    validationDateString = "2023-12-15T12:30:45";
	    String pattern = "yyyy-MM-dd'T'HH:mm:ss";
	    LocalDateTime validationDate = convertStringToLocalDateTime(validationDateString, pattern);

	    mmId = 2;
	    int statusId = 2; // Le status id passe de 1 à 2 comme le modèle id

	    GstStatusModelServiceProviderDTO gstStatusModelServiceProviderDTO = new GstStatusModelServiceProviderDTO();

	    gstStatusModelServiceProviderDTO.setStatusMspFkServiceProviderId(serviceProviderId);
	    gstStatusModelServiceProviderDTO.setStatusMspFkMessageModelId(mmId);
	    gstStatusModelServiceProviderDTO.setStatusMspFkStatusId(statusId);
	    gstStatusModelServiceProviderDTO.setStatusMspValidationDate(validationDate);

	    GstStatusModelServiceProvider gstStatusModelServiceProvider = gstStatusModelServiceProviderDtoMapper.toGstStatusModelServiceProvider(gstStatusModelServiceProviderDTO);

	    int isGstStatusModelServiceProviderUpdated = emailReminderMapper.updateGstStatusModelServiceProvider(gstStatusModelServiceProvider);

	    if (isGstStatusModelServiceProviderUpdated == 0) {
	        log.error("Erreur de mise à jour de la table intermédiaire des prestataires");
	        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table intermédiaire des prestataires");
	    }
	    log.info("Table intermédiaire des prestataires mise à jour avec succès");
	    return "Table intermédiaire des prestataires mise à jour avec succès";
	}
}
