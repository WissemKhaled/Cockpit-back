package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
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
import com.example.demo.service.MessageModelService;
import com.example.demo.service.ModelTrackingService;

@Service
public class ModelTrackingServiceImpl implements ModelTrackingService {
	
	private final ModelTrackingMapper modelTrackingMapper;
	private final ModelTrackingDtoMapper modelTrackingDtoMapper;
	private final ServiceProviderMapper serviceProviderMapper;
	private final MessageModelService messageModelService;
	private static final Logger log = LoggerFactory.getLogger(ModelTrackingServiceImpl.class);
	
	public ModelTrackingServiceImpl(ModelTrackingMapper modelTrackingMapper,
			ModelTrackingDtoMapper modelTrackingDtoMapper, ServiceProviderMapper serviceProviderMapper, MessageModelService messageModelService) {
		this.modelTrackingMapper = modelTrackingMapper;
		this.modelTrackingDtoMapper = modelTrackingDtoMapper;
		this.serviceProviderMapper = serviceProviderMapper;
		this.messageModelService = messageModelService;
	}

	/**
	 * Méthode pour convertir une date de type String vers le type LocalDateTime
	 */
	public static LocalDateTime convertStringToLocalDateTime(String dateString, String pattern) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
	    return LocalDateTime.parse(dateString, formatter);
	}
	
	public String saveModelTracking(ModelTrackingDTO modelTrackingDTO) throws DatabaseQueryFailureException {
		try {
			if (modelTrackingDTO == null) {
				log.warn("Le paramètre modelTrackingDTO ne peux être null");
				throw new EntityNotFoundException("Le paramètre modelTrackingDTO ne peux être null");
			}
			
			ModelTracking modelTracking = modelTrackingDtoMapper.toModelTracking(modelTrackingDTO);
			int isModelTrackingDTOInserted = modelTrackingMapper.insertGstModelTracking(modelTracking);
			
			if (isModelTrackingDTOInserted == 0) {
				log.error("Echec de l'insertion de l'objet en bdd");
				throw new DatabaseQueryFailureException("Echec de l'insertion de l'objet en bdd");
			}
			
			log.info("modelTracking inséré avec succès en db");
			return "modelTracking inséré avec succès en db";
		} catch(EntityNotFoundException ex) {
			throw new EntityNotFoundException(ex.getMessage());
		} catch(DatabaseQueryFailureException ex) {
			throw new DatabaseQueryFailureException(ex.getMessage());
		}
		
	}
	
	/*
	 * Méthode qui retourne chaque modèle de demande associé à son modèle de relance par mmLink similaire
	**/
	public List<Pair<MessageModel, MessageModel>> getAllPairsDemandAndItsRelaunchMessageModel() {
	    // Groupement des MessageModel par mmLink
	    List<MessageModel> allMessageModels = messageModelService.getAllMessageModels();
	    Map<Integer, List<MessageModel>> groupedByLink = allMessageModels.stream()
	            .collect(Collectors.groupingBy(MessageModel::getMmLink));

	    List<Pair<MessageModel, MessageModel>> pairs = new ArrayList<>();

	    // Itérer sur chaque groupe de MessageModel ayant le même mmLink
	    for (List<MessageModel> group : groupedByLink.values()) {
	        // Vérification que le groupe a au moins 2 éléments (2 messages ayant le même mmLink)
	        if (group.size() >= 2) {
	            // Accès à la paire de modèles ayant le même mmLink
	            MessageModel demands = group.get(0);
	            MessageModel relaunches = group.get(1);

	            pairs.add(Pair.of(demands, relaunches));
	        }
	    }

	    // Si aucune paire demande/relance n'est trouvée on retourne une liste vide
	    if (pairs.isEmpty()) {
	        log.error("Aucune paire de message modèle de relance et demande trouvée");
	        throw new EntityNotFoundException("Aucune paire de message modèle de relance et demande trouvée");
	    }
	    return pairs;
	}
	
	/*
	 * Méthode qui met à jour le status d'un message model dans la table gst_model_tracking selon l'Id du message model et l'Id du contract
	**/
	@Override
	public String updateModelTrackingDemand(int mmId, int statusId, int contractId, String validationDateString) throws DatabaseQueryFailureException {
		List<Pair<MessageModel, MessageModel>> allPairs = getAllPairsDemandAndItsRelaunchMessageModel();
	    
	    for (Pair<MessageModel, MessageModel> pair : allPairs) {
	        MessageModel demand = pair.getFirst();
	        MessageModel relaunch = pair.getSecond();
	        
	        // On vérifie si demands a un mmId = à celui passé en paramètre
	        // On vérifie également si la relance a un mmId = au mmId passé en param + 1
	        if (demand.getMmId() == mmId || relaunch.getMmId() == mmId + 1 ) {
	        	ModelTrackingDTO modelTrackingDTODemand = modelTrackingMapper.findModelTrackingInfoByContractIdAndMmId(contractId, demand.getMmId());
		        ModelTrackingDTO modelTrackingDTORelaunch = modelTrackingMapper.findModelTrackingInfoByContractIdAndMmId(contractId, relaunch.getMmId());
			    
			    if (modelTrackingDTODemand == null || modelTrackingDTORelaunch == null) {
			        log.error("Le suivi selon le contrat avec l'ID " + contractId + " n'a pas été trouvé");
			        throw new EntityNotFoundException("Le suivi selon le contrat avec l'ID " + contractId + " n'a pas été trouvé");
			    }
			    
			    // modelTrackingDTODemand
			    modelTrackingDTODemand.setMtFkContractId(modelTrackingDTODemand.getMtFkContractId());
			    modelTrackingDTODemand.setMtFkMessageModelId(modelTrackingDTODemand.getMtFkMessageModelId());
			    modelTrackingDTODemand.setMtFkCategoryId(modelTrackingDTODemand.getMtFkCategoryId());
			    
			    // modelTrackingDTORelaunch
			    modelTrackingDTORelaunch.setMtFkContractId(modelTrackingDTORelaunch.getMtFkContractId());
			    modelTrackingDTORelaunch.setMtFkMessageModelId(modelTrackingDTORelaunch.getMtFkMessageModelId());
			    modelTrackingDTORelaunch.setMtFkCategoryId(modelTrackingDTORelaunch.getMtFkCategoryId());

			    // si l'ID du status reçu du front = 2, on l'update à 2 et on update le mmId à 2 également dans la table intermédiaire et on update la date d'envoi
			    // si on reçoit une date de validation du front, on update l'ID du status à 3 et on update le mmId à 3 également dans la table intermédiaire et on update la date de validation
			    if (statusId == 2) {
			    	// Maj status de la demande
			    	modelTrackingDTODemand.setMtFkStatusId(statusId);
			    	modelTrackingDTODemand.setMtSendDate(LocalDateTime.now());

			        // 7 jours après date envoie, relance
			    } else if (validationDateString != null) {
			    	// Maj status de la demande
			    	modelTrackingDTODemand.setMtFkStatusId(3);
			    	modelTrackingDTODemand.setMtSendDate(modelTrackingDTODemand.getMtSendDate());
			    	
			    	// Conversion de la date de type string vers le type LocalDateTime avant insertion en BDD
			        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
			        LocalDateTime validationDate = convertStringToLocalDateTime(validationDateString, pattern);
			        modelTrackingDTODemand.setMtValidationDate(validationDate);
			    	
			        // Maj status de la relance
			    	modelTrackingDTORelaunch.setMtFkStatusId(3);
			    }
			    
			    ModelTracking modelTrackingDemand = modelTrackingDtoMapper.toModelTracking(modelTrackingDTODemand);
			    ModelTracking modelTrackingRelaunch = modelTrackingDtoMapper.toModelTracking(modelTrackingDTORelaunch);

			    int isDemandModelTrackingUpdated = modelTrackingMapper.updateModelTracking(modelTrackingDemand);
			    int isRelaunchModelTrackingUpdated = modelTrackingMapper.updateModelTracking(modelTrackingRelaunch);

			    if (isDemandModelTrackingUpdated == 0) {
			        log.error("Erreur de mise à jour de la table ModelTracking pour le contractId " + contractId + " et le message model demand avec mmId = " + modelTrackingDemand.getMtFkMessageModelId());
			        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table ModelTracking pour le contractId " + contractId + " et le message model demand avec mmId = " + modelTrackingDemand.getMtFkMessageModelId());
			    } else if (isRelaunchModelTrackingUpdated == 0) {
			    	log.error("Erreur de mise à jour de la table ModelTracking pour le contractId " + contractId + " et le message model relaunch avec mmId = " + modelTrackingRelaunch.getMtFkMessageModelId());
			        throw new DatabaseQueryFailureException("Erreur de mise à jour de la table ModelTracking pour le contractId " + contractId + " et le message model relaunch avec mmId = " + modelTrackingRelaunch.getMtFkMessageModelId());
			    } else if (isDemandModelTrackingUpdated > 0) {
			    	log.info("Table ModelTracking mise à jour avec succès pour le contractId " + contractId + " et le message model demand avec mmId = " + modelTrackingDemand.getMtFkMessageModelId());
				    return "Table ModelTracking mise à jour avec succès pour le contractId " + contractId + " et le message model deman avec mmId = " + modelTrackingDemand.getMtFkMessageModelId();
			    } else if (isRelaunchModelTrackingUpdated > 0) {
			    	log.info("Table ModelTracking mise à jour avec succès pour le contractId " + contractId + " et le message model relaunch avec mmId = " + modelTrackingRelaunch.getMtFkMessageModelId());
				    return "Table ModelTracking mise à jour avec succès pour le contractId " + contractId + " et le message model relaunch avec mmId = " + modelTrackingRelaunch.getMtFkMessageModelId();
			    }
	        }
		}
	    log.error("Aucune paire de message modèle de relance et demande trouvée pour mmId " + mmId);
	    throw new EntityNotFoundException("Aucune paire de message modèle de relance et demande trouvée pour mmId " + mmId);
	}

	@Override
	public void checkRelaunch(int statusId) {
	    List<Pair<MessageModel, MessageModel>> allPairs = getAllPairsDemandAndItsRelaunchMessageModel();
	    
	    for (Pair<MessageModel, MessageModel> pair : allPairs) {
	        MessageModel demand = pair.getFirst();
	        MessageModel relaunch = pair.getSecond();
	        
	        List<ModelTrackingDTO> modelTrackingDTODemandList = modelTrackingMapper.findModelTrackingInfoByMmId(demand.getMmId());
			 List<ModelTrackingDTO> modelTrackingDTORelaunchList = modelTrackingMapper.findModelTrackingInfoByMmId(relaunch.getMmId());
			 
			 int size = Math.min(modelTrackingDTODemandList.size(), modelTrackingDTORelaunchList.size());
			 
			 for (int i = 0; i < size; i++) {
				 ModelTrackingDTO modelTrackingDTODemand = modelTrackingDTODemandList.get(i);
				 
				// On retrouve la relance correspondant à la demande dans la table gst_tracking grâce au mtId de la demande + 1
			    ModelTrackingDTO modelTrackingDTORelaunch = modelTrackingDTORelaunchList.stream()
			            .filter(relaunchModel  -> relaunchModel .getMtId() == modelTrackingDTODemand.getMtId() + 1)
			            .findFirst()
			            .orElse(null);
			     
			     // Effectuer des opérations sur la paire (model1, model2)
				 if (modelTrackingDTODemand != null && modelTrackingDTORelaunch != null) {
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

				                    ModelTracking modelTrackingRelaunch = modelTrackingDtoMapper.toModelTracking(modelTrackingDTORelaunch);

				                    modelTrackingMapper.updateModelTracking(modelTrackingRelaunch);

				                    log.info("Relance : Table ModelTracking mise à jour pour l'id : " + modelTrackingRelaunch.getMtId());
				                } else {
				                    // log.error("Date d'envoi nulle ou < 7 jours");
				                }
				            }
			        	// si le status est en validé (3), on passe le status des demandes de 3 à 1 et le status des relances de 3 à 5 pour les modèles kbis à 5 mois et demi de la date de validation
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
				                    // log.error("Date d'envoi nulle ou < 5 mois et demi");
				                }
				            }
				        }
				    } catch (Exception e) {
				        log.error("Une erreur est survenue lors de la vérification de relance : " + e.getMessage(), e);
				    }
					 
				 } else {
				     log.warn("modelTrackingDTODemand et modelTrackingDTORelaunch null");
				 }
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
		
		List<ModelTrackingDTO> modelTrackingDTO = modelTrackingMapper.getModelTrackingInfoByContractId(contractId);
		
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
	
	@Override
	public String updateSubcontractorOrSpStatusId(Integer subcontractorId, Integer serviceProviderId) throws DatabaseQueryFailureException {
		if (subcontractorId != null) {
			int isSubcontractorStatusUpdated = modelTrackingMapper.updateSubcontractorStatus(subcontractorId);
			
			if (isSubcontractorStatusUpdated == 0) {
				log.error(String.format("Echec de la mise à jour du status du sous-traitant dont l'ID = %d en bdd", subcontractorId));
				throw new DatabaseQueryFailureException(String.format("Echec de la mise à jour du status du sous-traitant dont l'ID = %d en bdd", subcontractorId));
			}
			log.info(String.format("mise à jour dans la bdd du status du sous-traitant dont l'ID = %d effectuée avec succès", subcontractorId));
			return String.format("mise à jour dans la bdd du status du sous-traitant dont l'ID = %d effectuée avec succès", subcontractorId);
		}
		
		if (serviceProviderId != null) {
			int isServiceProviderStatusUpdated = modelTrackingMapper.updateServiceProvider(serviceProviderId);
			
			if (isServiceProviderStatusUpdated == 0) {
				log.error(String.format("Echec de la mise à jour du status du prestataire dont l'ID = %d en bdd", serviceProviderId));
				throw new DatabaseQueryFailureException(String.format("Echec de la mise à jour du status du prestataire dont l'ID = %d en bdd", serviceProviderId));
			}
			log.info(String.format("mise à jour dans la bdd du status du prestataire dont l'ID = %d effectuée avec succès", serviceProviderId));
			return String.format("mise à jour dans la bdd du status du prestataire dont l'ID = %d effectuée avec succès", serviceProviderId);
		}
		
		log.error("Les IDs du sous-traitant et du prestataire sont tous les deux null");
	    throw new IllegalArgumentException("Les IDs du sous-traitant et du prestataire sont tous les deux null");
	}
}
