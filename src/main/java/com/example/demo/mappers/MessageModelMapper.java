package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {



	List<MessageModel> getAllMessageModelBySubcontractorId(
	        @Param("subContractorId") Integer subContractorId);

	List<MessageModel> getAllMessageModelByServiceProviderId(
	        @Param("serviceProviderId") Integer serviceProviderId);
}
