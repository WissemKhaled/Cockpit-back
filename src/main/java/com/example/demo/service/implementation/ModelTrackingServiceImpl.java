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

	    // si l'ID du status reçu du front = 2, on l'update à 2 et on update le mmId à 2 également dans la table intermédiaire et on update la date d'envoi
	    // si on reçoit une date de validation du front, on update l'ID du status à 3 et on update le mmId à 3 également dans la table intermédiaire et on update la date de validation
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
        
        // System.out.println(groupedByLink);

        // Itérer sur chaque groupe de MessageModel ayant le même mmLink
        for (List<MessageModel> group : groupedByLink.values()) {
        	// Vérification que le groupe a au moins 2 éléments (2 messages ayant le même mmLink)
            if (group.size() >= 2) {
            	// Accès à la paire de modèles ayant le même mmLink
                MessageModel demand = group.get(0);
                MessageModel relaunch = group.get(1);

                // Effectuer des opérations sur la paire (model1, model2)
               performOperationsOnPair(demand, relaunch, contractId, statusId);
            }
        }
	}
	
	 private void performOperationsOnPair(MessageModel demand, MessageModel relaunch, int contractId, int statusId) {
//		 ModelTrackingDTO modelTrackingDTODemand = modelTrackingMapper.findModelTrackingInfoByContractIdAndMmId(demand.getMmId(), contractId);
//		 ModelTrackingDTO modelTrackingDTORelaunch = modelTrackingMapper.findModelTrackingInfoByContractIdAndMmId(relaunch.getMmId(), contractId);
		 
		 List<ModelTrackingDTO> modelTrackingDTODemandList = modelTrackingMapper.findModelTrackingInfo(demand.getMmId());
		 List<ModelTrackingDTO> modelTrackingDTORelaunchList = modelTrackingMapper.findModelTrackingInfoByMmId(relaunch.getMmId());
		 
		 int size = Math.min(modelTrackingDTODemandList.size(), modelTrackingDTORelaunchList.size());
		 
		 for (int i = 0; i < size; i++) {
			 ModelTrackingDTO modelTrackingDTODemand = modelTrackingDTODemandList.get(i);
		     ModelTrackingDTO modelTrackingDTORelaunch = modelTrackingDTORelaunchList.get(i);
		     
		     // Effectuer des opérations sur la paire (model1, model2)
	        System.out.println("Effectuer des opérations sur la paire models avec mmLink: " + relaunch.getMmLink());
	        System.out.println("Model demande: " + modelTrackingDTODemand);
	        System.out.println("Model relance: " + modelTrackingDTORelaunch);
		        
			 if (modelTrackingDTODemand != null && modelTrackingDTORelaunch != null) {
				 log.info("modelTrackingDTODemand et modelTrackingDTORelaunch récupéré pour le contractId: " + contractId);
				 
				    try {
			        LocalDateTime currentDate = LocalDateTime.now();
			        
			        // si le status est en cours, on passe le status de 5 à 1 pour tout types de modèles de relance à 7 jours de la date d'envoi
			        if (statusId == 1) {
			        	if (modelTrackingDTODemand.getMtFkCategoryId() == 1 || modelTrackingDTODemand.getMtFkCategoryId() == 2 || modelTrackingDTODemand.getMtFkCategoryId() == 3 || modelTrackingDTODemand.getMtFkCategoryId() == 4) {
			                if (modelTrackingDTODemand.getMtSendDate() != null && modelTrackingDTODemand.getMtSendDate().plusDays(7).isBefore(currentDate)) {
			                	// Maj du statusId de la table gst_model_tracking pour les relances
			                	modelTrackingDTORelaunch.setMtFkContractId(modelTrackingDTORelaunch.getMtFkContractId());
			                	modelTrackingDTORelaunch.setMtFkMessageModelId(modelTrackingDTORelaunch.getMtFkMessageModelId());
			                	modelTrackingDTORelaunch.setMtFkStatusId(1);
			                	modelTrackingDTORelaunch.setMtSendDate(modelTrackingDTORelaunch.getMtSendDate());
			                	modelTrackingDTORelaunch.setMtValidationDate(modelTrackingDTORelaunch.getMtValidationDate());

			                    ModelTracking modelTracking = modelTrackingDtoMapper.toModelTracking(modelTrackingDTORelaunch);

			                    modelTrackingMapper.updateModelTracking(modelTracking);

			                    log.info("Relance : Table ModelTracking mise à jour pour l'id : " + modelTracking.getMtId());
			                } else {
			                    log.error("Date d'envoi nulle ou < 7 jours");
			                }
			            }
		        	// si le status est en validé, on passe le status des demandes de 3 à 1 et le status des relances de 3 à 5 pour les modèles kbis à 5 mois et demi de la date de validation
			        } else if(statusId == 3) {
			        	if (modelTrackingDTODemand.getMtFkCategoryId() == 3) {
			        		if (modelTrackingDTODemand.getMtValidationDate() != null && modelTrackingDTODemand.getMtValidationDate().plusMonths(5).plusDays(15).isBefore(currentDate)) {
			                	// Maj du statusId de la table gst_model_tracking pour les demandes
			                	modelTrackingDTODemand.setMtFkContractId(modelTrackingDTODemand.getMtFkContractId());
			                	modelTrackingDTODemand.setMtFkMessageModelId(modelTrackingDTODemand.getMtFkMessageModelId());
			                	modelTrackingDTODemand.setMtFkStatusId(1);
			                	modelTrackingDTODemand.setMtSendDate(modelTrackingDTODemand.getMtSendDate());
			                	modelTrackingDTODemand.setMtValidationDate(modelTrackingDTODemand.getMtValidationDate());
			                	
			                	ModelTracking modelTrackingDemand = modelTrackingDtoMapper.toModelTracking(modelTrackingDTODemand);
			                	
			                	modelTrackingMapper.updateModelTracking(modelTrackingDemand);
			                	
			                	log.info("Maj Demande : Table ModelTracking mise à jour pour l'id : " + modelTrackingDemand.getMtId());
			                	
			                	// Maj du statusId de la table gst_model_tracking pour les relances
			                	modelTrackingDTORelaunch.setMtFkContractId(modelTrackingDTORelaunch.getMtFkContractId());
			                	modelTrackingDTORelaunch.setMtFkMessageModelId(modelTrackingDTORelaunch.getMtFkMessageModelId());
			                	modelTrackingDTORelaunch.setMtFkStatusId(5);
			                	modelTrackingDTORelaunch.setMtSendDate(modelTrackingDTORelaunch.getMtSendDate());
			                	modelTrackingDTORelaunch.setMtValidationDate(modelTrackingDTORelaunch.getMtValidationDate());

			                    ModelTracking modelTrackingRelaunch = modelTrackingDtoMapper.toModelTracking(modelTrackingDTORelaunch);

			                    modelTrackingMapper.updateModelTracking(modelTrackingRelaunch);

			                    log.info("Maj Relance : Table ModelTracking mise à jour pour l'id : " + modelTrackingRelaunch.getMtId());
			                } else {
			                    log.error("Date d'envoi nulle ou < 5 mois et demi");
			                }
			            }
			        }
			    } catch (Exception e) {
			        log.error("Une erreur est survenue lors de la vérification de relance : " + e.getMessage(), e);
			        // return "Une erreur est survenue lors de la vérification de relance : " + e.getMessage();
			    }
				 
			 } else {
				 System.out.println("Effectuer des opérations sur la paire models avec mmLink: " + relaunch.getMmLink());
			     System.out.println("Model demande: " + modelTrackingDTODemand);
			     System.out.println("Model relance: " + modelTrackingDTORelaunch);
			     log.warn("modelTrackingDTODemand et modelTrackingDTORelaunch null pour le contractId: " + contractId);
			 }
		 }
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
