package com.example.demo.service;
import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface EmailReminderSubcontractorService {
	public String updateSubcontractorStatusFromInProgressToInValidation(int mmId, int statusId, int subcontractorId, String validationDateString) throws DatabaseQueryFailureException;

	public GstStatusModelSubcontractorDTO getSubcontractorReminderInfoBySId(int subcontractorId);
}
