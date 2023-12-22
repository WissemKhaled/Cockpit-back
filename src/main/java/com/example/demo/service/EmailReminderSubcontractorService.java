package com.example.demo.service;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface EmailReminderSubcontractorService {
	public String updateSubcontractorStatusFromInProgressToInValidation(String validationDate, int mmId, int subcontractorId) throws DatabaseQueryFailureException;
}
