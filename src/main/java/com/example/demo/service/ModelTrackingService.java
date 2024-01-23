package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface ModelTrackingService {
	String saveModelTracking(ModelTrackingDTO modelTrackingDTO) throws DatabaseQueryFailureException;
	
String updateModelTrackingDemand(int mmId, int statusId, int contractId, String validationDate) throws DatabaseQueryFailureException;
//	
//	List<ModelTrackingDTO> getModelTrackingInfoByContractId(int contractId);
//	
//	ModelTrackingDTO getModelTrackingInfoByContractIdAndMmId(int contractId, int mmId);
	
	/**
	 * Vérifie si une relance doit être faite et met à jour les status en se basant sur la table gst_model_tracking
	 *
	 * @param statusId      l'id du status du modèle dont on veut vérifier la relance.
	 * @return void
	 * @throws DatabaseQueryFailureException 
	 */
	void checkRelaunch(int statusId);
	
	/**
	 * Met à jour le statut du sous-traitant ou prestataire en se basant sur la table gst_model_tracking
	 * Cette méthode sert à définir le status de la table gst_subcontractor ou gst_service_provider selon le status dnas la table gst_model_tracking
	 * Dans la table tracking, si il y a au moins un status en cours (1), alors le status du sous-traiant ou prestataire correspondant sera défini à 1
	 * Dans la table tracking, si il y a au moins un status en validation (2) et aucun statut en cours, alors le status du sous-traiant ou prestataire correspondant sera défini à 2
	 * Dans la table tracking, si tous les status sont validés (3), alors le status du sous-traiant ou prestataire correspondant sera défini à 3
	 * 
	 * l'id du sous-traitant ou prestataire est optionnel mais l'un des deux doit être passé en param
	 *
	 * @param subcontractorId      l'id du sous-traitant dont on veut les status (optionel).
	 * @param subcontractorId      l'id du sous-traitant dont on veut les status (optionel).
	 * @return La liste des statusId du sous-traitant dont on a passé l'ID.
	 * @throws DatabaseQueryFailureException 
	 */
//	String updateSubcontractorOrSpStatusId(Integer subcontractorId, Integer serviceProviderId) throws DatabaseQueryFailureException;
}
