package com.example.demo.service;

import java.time.LocalDateTime;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.dto.MessageModelDTO;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.CreateMessageModelDtoMapper;
import com.example.demo.mappers.MessageModelDtoMapper;
import com.example.demo.mappers.MessageModelMapper;

import lombok.extern.java.Log;

@Log
@Service
public class MessageModelServiceImpl implements MessageModelService {
	@Autowired
	MessageModelMapper messageModelMapper;
	
	@Autowired
	private CreateMessageModelDtoMapper createMessageModelDtoMapper;
	
	@Autowired 
	private MessageModelDtoMapper messageModelDtoMapper;

	 @Override
	    public String saveMessageModel(CreateMessageModelDTO createMessageModelDto) throws GeneralException {
	        try {
	            if (createMessageModelDto != null) {
	                MessageModel messageModel = createMessageModelDtoMapper.toMessageModel(createMessageModelDto);
	                messageModel.setMmCreationDate(LocalDateTime.now());

	                int isMessageModelInserted = messageModelMapper.insertMessageModel(messageModel);

	                if (isMessageModelInserted == 0) {
	                    log.severe("Failed to insert message model into the database");
	                    throw new GeneralException("Failed to insert message model into the database");
	                }
	                log.info("Message model created successfully");
	                return "Message model created successfully";
	            } else {
	                log.severe("Parameter createMessageModelDto cannot be null");
	                throw new IllegalArgumentException("Parameter createMessageModelDto cannot be null");
	            }
	        } catch (Exception e) {
	            log.severe(e.getMessage());
	            throw new GeneralException("Error during the creation of the message model");
	        }
	    }


	 @Override
	 public MessageModelDTO getMessageModelByType(String mmType) throws NotFoundException {
	     if (mmType != null && !mmType.isEmpty()) {
	         MessageModel messageModel = messageModelMapper.getMessageModelByType(mmType);
	         if (messageModel != null) {
	             MessageModelDTO messageModelDTO = messageModelDtoMapper.toDto(messageModel);
	             return messageModelDTO;
	         } else {
	             throw new NotFoundException("No message model found for type: " + mmType);
	         }
	     } else {
	         throw new IllegalArgumentException("mmType cannot be empty or null");
	     }
	 }
}
