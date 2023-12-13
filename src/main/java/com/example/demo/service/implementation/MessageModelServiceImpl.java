package com.example.demo.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.MessageModelMapper;
import com.example.demo.service.MessageModelService;

@Service
public class MessageModelServiceImpl implements MessageModelService {

	private final MessageModelMapper messageModelMapper;

	public MessageModelServiceImpl(MessageModelMapper mapper) {
		this.messageModelMapper = mapper;
	}

	@Override
	public List<MessageModel> getAllMessageModelWhitStatus(Integer statusId) {

		List<MessageModel> messageModel = messageModelMapper.getAllMessageModelWhitStatus(statusId);
		if (messageModel.isEmpty()) {
			throw new MessageModelNotFoundException("aucun modèle de mail n'existe pour ce status !");
		}
		return messageModel;
	}

}
