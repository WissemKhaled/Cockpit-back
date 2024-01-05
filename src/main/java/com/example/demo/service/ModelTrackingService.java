package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface ModelTrackingService {
//	public String updateServiceProviderStatusFromInProgressToInValidation(int mmId, int statusId, int serviceProviderId, String validationDate) throws DatabaseQueryFailureException;
	
	public List<ModelTrackingDTO> getServiceProviderReminderInfoBySpId(int serviceProviderId);
	
	public ModelTrackingDTO getSpReminderInfoBySpIdAndMmId(int serviceProviderId, int mmId);
	
//	public void checkRelaunchServiceProvider(Page<MessageModel> messageModels, int serviceProviderId);
}
