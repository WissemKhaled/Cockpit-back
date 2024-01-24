package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface ModelTrackingService {
	String saveModelTracking(ModelTrackingDTO modelTrackingDTO) throws DatabaseQueryFailureException;
	
	String updateModelTrackingDemand(int mmId, int statusId, int contractId, String validationDate) throws DatabaseQueryFailureException;
	
	List<ModelTrackingDTO> getModelTrackingInfoByContractId(int contractId);
	
	ModelTrackingDTO getModelTrackingInfoByContractIdAndMmId(int contractId, int mmId);
	
	List<ModelTrackingDTO> getAllMessageModelByServiceProviderId(int serviceProviderId, int statusId);
	
	List<ModelTrackingDTO> getAllMessageModelBySubcontractorId(int subcontractorId, int statusId);
	
	void checkRelaunch(int statusId);
}
