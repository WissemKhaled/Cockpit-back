package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.MessageModel;
import org.apache.ibatis.annotations.Param;

public interface MessageModelService {
		
//	List<MessageModel> getAllMessageModel();
//
//	List<MessageModel> getAllMessageModelsAndStatusByServiceProviderId(Integer serviceproviderId);
//
//	List<MessageModel> getAllMessageModelsAndStatusBySubcontractorCategoryAndId(Integer subcontractorId);

	List<MessageModel> getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId( Integer subContractorStatusId,Integer serviceProviderStatusId, Integer subContractorId,Integer serviceProviderId);
	
	public List<MessageModel> getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(Integer subContractorId, int categoryId);
}
