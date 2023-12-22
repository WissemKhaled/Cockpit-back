package com.example.demo.service;

import com.example.demo.exception.DatabaseQueryFailureException;

public interface EmailReminderServiceProviderService {
	public String updateServiceProviderStatusFromInProgressToInValidation(String validationDate, int mmId, int serviceProviderId) throws DatabaseQueryFailureException;
}
