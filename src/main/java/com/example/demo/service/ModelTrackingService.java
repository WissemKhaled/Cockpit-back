package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface ModelTrackingService {
	public String updateModelTrackingDemand(int mmId, int statusId, int contractId, String validationDate) throws DatabaseQueryFailureException;
	
	public List<ModelTrackingDTO> getModelTrackingInfoByContractId(int contractId);
	
	public ModelTrackingDTO getModelTrackingInfoByContractIdAndMmId(int contractId, int mmId);
	
	public void checkRelaunch(Page<MessageModel> messageModels, int serviceProviderId);
}
