package com.example.demo.service;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.dto.MessageModelDTO;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.GeneralException;

public interface MessageModelService {
	MessageModelDTO getMessageModelById(int mmId) throws NotFoundException;
	
	String saveMessageModel(CreateMessageModelDTO createMessageModelDto) throws GeneralException;
	
	String editMessageModel(int mmId, MessageModel messageModel) throws GeneralException;
	
	List<MessageModelDTO> getMessageModelByType(String mmType) throws NotFoundException;
}
