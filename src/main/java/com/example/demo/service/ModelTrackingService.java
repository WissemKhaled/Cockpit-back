package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface ModelTrackingService {
	String saveModelTracking(ModelTrackingDTO modelTrackingDTO) throws DatabaseQueryFailureException;
	
	String updateModelTrackingDemand(int mmId, int statusId, int contractId, String validationDate) throws DatabaseQueryFailureException;
	
	List<ModelTrackingDTO> getModelTrackingInfoByContractId(int contractId);
	
	ModelTrackingDTO getModelTrackingInfoByContractIdAndMmId(int contractId, int mmId);
	
	void checkRelaunch(int statusId);
	
	/**
	 * Récupère depuis la table gst_tracking la liste de tout les statusId des demandes liées au sous-traitant
	 *
	 * @param subcontractorId      l'id du sous-traitant dont on veut les status.
	 * @return La liste des statusId du sous-traitant dont on a passé l'ID.
	 * @throws DatabaseQueryFailureException 
	 */
	void updateSubcontractorStatusIdBySId(int subcontractorId) throws DatabaseQueryFailureException;
}
