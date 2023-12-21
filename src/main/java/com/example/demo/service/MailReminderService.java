package com.example.demo.service;

public interface MailReminderService {
	public int updateSubcontractorStatusFromInProgressToInValidation(String validationDate, int mmId, int subcontractorId);
}
