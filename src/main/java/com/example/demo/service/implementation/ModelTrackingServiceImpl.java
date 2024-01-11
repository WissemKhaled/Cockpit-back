package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.dto.mapper.ModelTrackingDtoMapper;
import com.example.demo.entity.MessageModel;
import com.example.demo.entity.ModelTracking;
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
	public String updateModelTrackingDemand(int mmId, int statusId, int contractId, String validationDateString) throws DatabaseQueryFailureException {
	    ModelTrackingDTO modelTrackingDTO = modelTrackingMapper.findModelTrackingInfoByContractIdAndMmId(contractId, mmId);
	    
	    if (modelTrackingDTO == null) {
	        log.error("Le suivi selon le contrat avec l'ID " + contractId + " n'a pas été trouvé");
	        throw new EntityNotFoundException("Le suivi selon le contrat avec l'ID " + contractId + " n'a pas été trouvé");
	    }

	    modelTrackingDTO.setMtFkContractId(contractId);
	    modelTrackingDTO.setMtFkMessageModelId(mmId);
	    modelTrackingDTO.setMtFkCategoryId(modelTrackingDTO.getMtFkCategoryId());

	    // si l'ID du status reçu du front = 1, on l'update à 2 et on update le mmId à 2 également dans la table intermédiaire et on update la date d'envoi
	    // si l'ID du status reçu du front = 2, on l'update à 3 et on update le mmId à 3 également dans la table intermédiaire et on update la date de validation
	    if (statusId == 2) {
	        modelTrackingDTO.setMtFkStatusId(statusId);
	        modelTrackingDTO.setMtSendDate(LocalDateTime.now());

	        // 7 jours après date envoie, relance
	    } else if (validationDateString != null) {
	        modelTrackingDTO.setMtFkStatusId(3);
	        modelTrackingDTO.setMtSendDate(modelTrackingDTO.getMtSendDate());

	        // Conversion de la date de type string vers le type LocalDateTime avant insertion en BDD
	        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
	        LocalDateTime validationDate = convertStringToLocalDateTime(validationDateString, pattern);
	        modelTrackingDTO.setMtValidationDate(validationDate);

	        // après 7 jours date validation, relance
	        // passer la relance à en cours
	    }

	    ModelTracking modelTracking = modelTrackingDtoMapper.toModelTracking(modelTrackingDTO);

	    int isModelTrackingUpdated = modelTrackingMapper.updateModelTracking(modelTracking);

	    if (isModelTrackingUpdated == 0) {
	        log.error("Erreur de mise à jour de la table ModelTracking pour le contractId " + contractId);
	        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table ModelTracking");
	    }
	    log.info("Table ModelTracking mise à jour avec succès pour le contractId " + contractId);
	    return "Table ModelTracking mise à jour avec succès";
	}
	
	@Override
	public void checkRelaunch(List<MessageModel> allMessages, int contractId, int statusId) {
		// Groupement des MessageModel par mmLink
        Map<Integer, List<MessageModel>> groupedByLink = allMessages.stream()
                .collect(Collectors.groupingBy(MessageModel::getMmLink));
        
        System.out.println(groupedByLink);

        // Itérer sur chaque groupe de MessageModel ayant le même mmLink
        for (List<MessageModel> group : groupedByLink.values()) {
        	// Vérification que le groupe a au moins 2 éléments (2 messages ayant le même mmLink)
            if (group.size() >= 2) {
            	// Accès à la paire de modèles ayant le même mmLink
                MessageModel model1 = group.get(0);
                MessageModel model2 = group.get(1);

                // Effectuer des opérations sur la paire (model1, model2)
               // performOperationsOnPair(model1, model2);
            }
        }
		
//	    try {
//	        LocalDateTime currentDate = LocalDateTime.now();
//
//	        List<ModelTrackingDTO> modelTrackingDTOList = modelTrackingMapper.findModelTrackingInfo(contractId);
//
//	        for (ModelTrackingDTO modelTrackingDTO : modelTrackingDTOList) {
//	        	// prendre en compte link
//	            if (modelTrackingDTO.getMtFkCategoryId() == 1 || modelTrackingDTO.getMtFkCategoryId() == 2) {
//	            	// Vérifie si la date d'envoi n'est pas nulle et n'a pas dépassée 7 jours et si le status = 2 (en validation) 
//	                if (modelTrackingDTO.getMtSendDate() != null && modelTrackingDTO.getMtSendDate().plusDays(7).isBefore(currentDate)) {
//	                	// Maj du statusId de la table gst_model_tracking
//	                    modelTrackingDTO.setMtFkContractId(contractId);
//	                    modelTrackingDTO.setMtFkMessageModelId(modelTrackingDTO.getMtFkMessageModelId());
//	                    modelTrackingDTO.setMtFkStatusId(1);
//	                    modelTrackingDTO.setMtSendDate(modelTrackingDTO.getMtSendDate());
//	                    modelTrackingDTO.setMtValidationDate(modelTrackingDTO.getMtValidationDate());
//
//	                    ModelTracking modelTracking = modelTrackingDtoMapper.toModelTracking(modelTrackingDTO);
//
//	                    modelTrackingMapper.updateModelTracking(modelTracking);
//
//	                    log.info("Relance : Table ModelTracking mise à jour pour l'id : " + modelTracking.getMtId());
//	                } else {
//	                    log.error("Date d'envoi nulle ou < 7 jours");
//	                }
//	            } else if (modelTrackingDTO.getMtFkCategoryId() == 3) {
//	            	
//	            } else if (modelTrackingDTO.getMtFkCategoryId() == 4) {
//	            	
//	            }
//	        }
//	        log.warn("Aucun enregistrement trouvé pour le contrat avec l'ID : " + contractId);
//	        return "Aucun enregistrement trouvé pour le contrat avec l'ID : " + contractId;
//
//	    } catch (Exception e) {
//	        log.error("Une erreur est survenue lors de la vérification de relance : " + e.getMessage(), e);
//	        return "Une erreur est survenue lors de la vérification de relance : " + e.getMessage();
//	    }
	}
	
	 private static void performOperationsOnPair(MessageModel model1, MessageModel model2) {
		// Effectuer des opérations sur la paire (model1, model2)
        System.out.println("Effectuer des opérations sur la paire models avec mmLink: " + model1.getMmLink());
        System.out.println("Model 1: " + model1);
        System.out.println("Model 2: " + model2);
	 }

	@Override
	public List<ModelTrackingDTO> getModelTrackingInfoByContractId(int contractId) {
		ServiceProvider isFoundContract = serviceProviderMapper.findServiceProviderById(contractId);
		
		if (isFoundContract == null) {
			log.error("Aucun contrat trouvé avec l'id " + contractId);
			throw new EntityNotFoundException("Aucun contrat trouvé avec l'id " + contractId);
		}
		
		List<ModelTrackingDTO> modelTrackingDTO = modelTrackingMapper.findModelTrackingInfo(contractId);
		
		return modelTrackingDTO;
	}
	
	@Override
	public ModelTrackingDTO getModelTrackingInfoByContractIdAndMmId(int contractId, int mmId) {
		ModelTrackingDTO modelTrackingDTO = modelTrackingMapper.findModelTrackingInfoByContractIdAndMmId(contractId, mmId);
		
		if (modelTrackingDTO == null) {
			log.error("Aucune info trouvé avec le contractId " + contractId + " et le message model ID " + mmId);
			throw new EntityNotFoundException("Aucune info trouvé avec le contractId " + contractId + " et le message model ID " + mmId);
		}
		
		return modelTrackingDTO;
	}
}
