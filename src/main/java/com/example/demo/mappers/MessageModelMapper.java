package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {


	List<MessageModel> getAllMessageModelBySubcontractorId(
	        Integer subContractorId);

	List<MessageModel> getAllMessageModelByServiceProviderId(
	        Integer serviceProviderId);

	List<MessageModel> getAllMessageModelByContractId(
			Integer contractId);
}
