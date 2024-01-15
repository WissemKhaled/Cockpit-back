package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.MessageModel;

public interface MessageModelService {

	public List<MessageModel> getAllMessageModelBySubcontractorId(Integer subContractorId);
	
	List<MessageModel> getAllMessageModelByServiceProviderId(Integer serviceProviderId);
}
