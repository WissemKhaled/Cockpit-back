package com.example.demo.service;

import com.example.demo.dto.GstStatusModelServiceProviderDTO;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface EmailReminderServiceProviderService {
	public String updateServiceProviderStatusFromInProgressToInValidation(int mmId, int statusId, int serviceProviderId, String validationDate) throws DatabaseQueryFailureException;
	
	public GstStatusModelServiceProviderDTO getServiceProviderReminderInfoBySpId(int serviceProviderId);
}
