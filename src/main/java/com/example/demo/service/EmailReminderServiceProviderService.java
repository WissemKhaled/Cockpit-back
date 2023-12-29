package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.GstStatusModelServiceProviderDTO;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface EmailReminderServiceProviderService {
	public String updateServiceProviderStatusFromInProgressToInValidation(int mmId, int statusId, int serviceProviderId, String validationDate) throws DatabaseQueryFailureException;
	
	public List<GstStatusModelServiceProviderDTO> getServiceProviderReminderInfoBySpId(int serviceProviderId);
	
	public GstStatusModelServiceProviderDTO getSpReminderInfoBySpIdAndMmId(int serviceProviderId, int mmId);
}
