package com.example.demo.service;

import org.apache.ibatis.javassist.NotFoundException;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.dto.MessageModelDTO;
import com.example.demo.exception.GeneralException;

public interface MessageModelService {
	String saveMessageModel(CreateMessageModelDTO createMessageModelDto) throws GeneralException;
	
	MessageModelDTO getMessageModelByType(String mmType) throws NotFoundException;
}
