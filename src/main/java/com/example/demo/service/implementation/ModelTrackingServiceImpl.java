package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.dto.mapper.ModelTrackingDtoMapper;
import com.example.demo.entity.ModelTracking;
import com.example.demo.entity.MessageModel;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.ModelTrackingMapper;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.service.ModelTrackingService;

@Service
public class ModelTrackingServiceImpl implements ModelTrackingService {
	
	private final ModelTrackingMapper modelTrackingMapper;
	private final ModelTrackingDtoMapper modelTrackingDtoMapper;
	private final ServiceProviderMapper serviceProviderMapper;
	private static final Logger log = LoggerFactory.getLogger(ModelTrackingServiceImpl.class);
	
	public ModelTrackingServiceImpl(ModelTrackingMapper modelTrackingMapper,
			ModelTrackingDtoMapper modelTrackingDtoMapper, ServiceProviderMapper serviceProviderMapper) {
		this.modelTrackingMapper = modelTrackingMapper;
		this.modelTrackingDtoMapper = modelTrackingDtoMapper;
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
	    ModelTrackingDTO modelTrackingDTO = modelTrackingMapper.findAlertByServiceProviderIdAndMmId(serviceProviderId, mmId);
	    
	    if (modelTrackingDTO == null) {
	        log.error("Le prestataire avec l'ID " + serviceProviderId + " n'a pas été trouvé");
	        throw new EntityNotFoundException("Le prestataire avec l'ID " + serviceProviderId + " n'a pas été trouvé");
	    }

	    modelTrackingDTO.setStatusMspFkServiceProviderId(serviceProviderId);
	    modelTrackingDTO.setStatusMspFkMessageModelId(mmId);

	    // si l'ID du status reçu du front = 1, on l'update à 2 et on update le mmId à 2 également dans la table intermédiaire et on update la date d'envoi
	    // si l'ID du status reçu du front = 2, on l'update à 3 et on update le mmId à 3 également dans la table intermédiaire et on update la date de validation
	    if (statusId == 2) {
	    	// gstStatusModelServiceProviderDTO.setStatusMspFkMessageModelId(2);
	        modelTrackingDTO.setStatusMspFkStatusId(2);
	        modelTrackingDTO.setStatusMspSentDate(LocalDateTime.now());

	        // 7 jours après date envoie, relance
	    } else if (validationDateString != null) {
	    	// gstStatusModelServiceProviderDTO.setStatusMspFkMessageModelId(3);
	        modelTrackingDTO.setStatusMspFkStatusId(3);
	        modelTrackingDTO.setStatusMspSentDate(modelTrackingDTO.getStatusMspSentDate());

	        // Conversion de la date de type string vers le type LocalDateTime avant insertion en BDD
	        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
	        LocalDateTime validationDate = convertStringToLocalDateTime(validationDateString, pattern);
	        modelTrackingDTO.setStatusMspValidationDate(validationDate);

	        // après 7 jours date validation, relance
	        // passer la relance à en cours
	    }

	    ModelTracking modelTracking = modelTrackingDtoMapper.toGstStatusModelServiceProvider(modelTrackingDTO);

	    int isModelTrackingUpdated = modelTrackingMapper.updateGstStatusModelServiceProvider(modelTracking);

	    if (isModelTrackingUpdated == 0) {
	        log.error("Erreur de mise à jour de la table intermédiaire des prestataires pour le serviceProviderId " + serviceProviderId);
	        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table intermédiaire des prestataires");
	    }
	    log.info("Table intermédiaire des prestataires mise à jour avec succès pour le serviceProviderId " + serviceProviderId);
	    return "Table intermédiaire des prestataires mise à jour avec succès";
	}
	
	@Override
	public void checkRelaunchServiceProvider(Page<MessageModel> messageModels, int serviceProviderId) {
		LocalDateTime currentDate = LocalDateTime.now();
		
		List<ModelTrackingDTO> gstStatusModelServiceProviderDTOList = modelTrackingMapper.findServiceProviderReminderInfo(serviceProviderId);
		
		for (MessageModel messageModel : messageModels) {
			if (messageModel.getMmType().contains("Relance")) {
				for (ModelTrackingDTO gstStatusModelServiceProviderDTO : gstStatusModelServiceProviderDTOList) {
					 // si la date de validation n'est pas null et date de 7 jours, on met à jour le status
					if (gstStatusModelServiceProviderDTO.getStatusMspValidationDate() != null && gstStatusModelServiceProviderDTO.getStatusMspValidationDate().plusDays(7).isBefore(currentDate)) {
						// on met à jour le statusId de la table intermédiaire
						gstStatusModelServiceProviderDTO.setStatusMspFkServiceProviderId(serviceProviderId);
					    gstStatusModelServiceProviderDTO.setStatusMspFkMessageModelId(gstStatusModelServiceProviderDTO.getStatusMspFkMessageModelId());
					    gstStatusModelServiceProviderDTO.setStatusMspFkStatusId(1);
					    gstStatusModelServiceProviderDTO.setStatusMspSentDate(gstStatusModelServiceProviderDTO.getStatusMspSentDate());
					    gstStatusModelServiceProviderDTO.setStatusMspValidationDate(gstStatusModelServiceProviderDTO.getStatusMspValidationDate());
					    
					    ModelTracking gstStatusModelServiceProvider = modelTrackingDtoMapper.toGstStatusModelServiceProvider(gstStatusModelServiceProviderDTO);
						
						modelTrackingMapper.updateGstStatusModelServiceProvider(gstStatusModelServiceProvider);
						
						log.info("Relance : Table intermédiaire mise à jour pour l'id : " + gstStatusModelServiceProvider.getStatusMspId());
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
	public List<ModelTrackingDTO> getServiceProviderReminderInfoBySpId(int serviceProviderId) {
		ServiceProvider isFoundServiceProvider = serviceProviderMapper.findServiceProviderById(serviceProviderId);
		
		if (isFoundServiceProvider == null) {
			log.error("Aucun prestataire trouvé avec l'id " + serviceProviderId);
			throw new EntityNotFoundException("Aucun prestataire trouvé avec l'id " + serviceProviderId);
		}
		
		List<ModelTrackingDTO> gstStatusModelServiceProviderDTO = modelTrackingMapper.findServiceProviderReminderInfo(serviceProviderId);
		
		return gstStatusModelServiceProviderDTO;
	}
	
	@Override
	public ModelTrackingDTO getSpReminderInfoBySpIdAndMmId(int serviceProviderId, int mmId) {
		ModelTrackingDTO gstStatusModelServiceProviderDTO = modelTrackingMapper.findAlertByServiceProviderIdAndMmId(serviceProviderId, mmId);
		
		if (gstStatusModelServiceProviderDTO == null) {
			log.error("Aucune info trouvé avec l'id prestataire " + serviceProviderId + " et le message model ID " + mmId);
			throw new EntityNotFoundException("Aucune info trouvé avec l'id prestataire " + serviceProviderId + " et le message model ID " + mmId);
		}
		
		return gstStatusModelServiceProviderDTO;
	}
}
