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
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.EmailReminderMapper;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.service.EmailReminderServiceProviderService;

@Service
public class EmailReminderServiceProviderServiceImpl implements EmailReminderServiceProviderService {
	
	private final EmailReminderMapper emailReminderMapper;
	private final GstStatusModelServiceProviderDtoMapper gstStatusModelServiceProviderDtoMapper;
	private final ServiceProviderMapper serviceProviderMapper;
	private static final Logger log = LoggerFactory.getLogger(EmailReminderServiceProviderServiceImpl.class);
	
	public EmailReminderServiceProviderServiceImpl(EmailReminderMapper emailReminderMapper,
			GstStatusModelServiceProviderDtoMapper gstStatusModelServiceProviderDtoMapper, ServiceProviderMapper serviceProviderMapper) {
		this.emailReminderMapper = emailReminderMapper;
		this.gstStatusModelServiceProviderDtoMapper = gstStatusModelServiceProviderDtoMapper;
		this.serviceProviderMapper = serviceProviderMapper;
	}

	/**
	 * Méthode pour convertir une date de type String vers le type LocalDateTime
	 */
	public static LocalDateTime convertStringToLocalDateTime(String dateString, String pattern) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
	    return LocalDateTime.parse(dateString, formatter);
	}
	
	@Override
	public String updateServiceProviderStatusFromInProgressToInValidation(int mmId, int statusId, int serviceProviderId, String validationDateString) throws DatabaseQueryFailureException {
	    GstStatusModelServiceProviderDTO gstStatusModelServiceProviderDTO = emailReminderMapper.findServiceProviderReminderInfo(serviceProviderId);

	    if (gstStatusModelServiceProviderDTO == null) {
	        log.error("Le prestataire avec l'ID " + serviceProviderId + " n'a pas été trouvé");
	        throw new EntityNotFoundException("Le prestataire avec l'ID " + serviceProviderId + " n'a pas été trouvé");
	    }

	    gstStatusModelServiceProviderDTO.setStatusMspFkServiceProviderId(serviceProviderId);
	    gstStatusModelServiceProviderDTO.setStatusMspFkMessageModelId(mmId);

	    // si l'ID du status reçu du front = 1, on l'update à 2 et on update le mmId à 2 également dans la table intermédiaire et on update la date d'envoi
	    // si l'ID du status reçu du front = 2, on l'update à 3 et on update le mmId à 3 également dans la table intermédiaire et on update la date de validation
	    if (statusId == 2) {
	        gstStatusModelServiceProviderDTO.setStatusMspFkStatusId(2);
	        gstStatusModelServiceProviderDTO.setStatusMspSentDate(LocalDateTime.now());

	        // 7 jours après date envoie, relance
	    } else if (validationDateString != null) {
	        gstStatusModelServiceProviderDTO.setStatusMspFkStatusId(3);
	        gstStatusModelServiceProviderDTO.setStatusMspSentDate(gstStatusModelServiceProviderDTO.getStatusMspSentDate());

	        // Conversion de la date de type string vers le type LocalDateTime avant insertion en BDD
	        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
	        LocalDateTime validationDate = convertStringToLocalDateTime(validationDateString, pattern);
	        gstStatusModelServiceProviderDTO.setStatusMspValidationDate(validationDate);

	        // après 7 jours date validation, relance
	        // passer la relance à en cours
	    }

	    GstStatusModelServiceProvider gstStatusModelServiceProvider = gstStatusModelServiceProviderDtoMapper.toGstStatusModelServiceProvider(gstStatusModelServiceProviderDTO);

	    int isGstStatusModelServiceProviderUpdated = emailReminderMapper.updateGstStatusModelServiceProvider(gstStatusModelServiceProvider);

	    if (isGstStatusModelServiceProviderUpdated == 0) {
	        log.error("Erreur de mise à jour de la table intermédiaire des prestataires pour le serviceProviderId " + serviceProviderId);
	        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table intermédiaire des prestataires");
	    }
	    log.info("Table intermédiaire des prestataires mise à jour avec succès pour le serviceProviderId " + serviceProviderId);
	    return "Table intermédiaire des prestataires mise à jour avec succès";
	}

	@Override
	public GstStatusModelServiceProviderDTO getServiceProviderReminderInfoBySpId(int serviceProviderId) {
		ServiceProvider isFoundServiceProvider = serviceProviderMapper.findServiceProviderById(serviceProviderId);
		
		if (isFoundServiceProvider == null) {
			throw new EntityNotFoundException("Aucun prestataire trouvé avec l'id " + serviceProviderId);
		}
		
		GstStatusModelServiceProviderDTO gstStatusModelServiceProviderDTO = emailReminderMapper.findServiceProviderReminderInfo(serviceProviderId);
		
		return gstStatusModelServiceProviderDTO;
	}
}
