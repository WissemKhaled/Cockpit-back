package com.example.demo.service;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.exception.GeneralException;

public interface MessageModelService {
	String saveMessageModel(CreateMessageModelDTO createMessageModelDto) throws GeneralException;
}
