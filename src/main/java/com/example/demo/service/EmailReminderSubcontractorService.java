
package com.example.demo.service;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface EmailReminderSubcontractorService {
	public String updateSubcontractorStatusFromInProgressToInValidation(int mmId, int statusId, int subcontractorId, String validationDateString) throws DatabaseQueryFailureException;

	public List<GstStatusModelSubcontractorDTO> getSubcontractorReminderInfoBySId(int subcontractorId);
	
	public GstStatusModelSubcontractorDTO getSubcontractorReminderInfoBySpIdAndMmId(int sId, int mmId);
	
	//public void checkRelaunchSubcontractor(Page<MessageModel> messageModels, int subcontractorId);
}
