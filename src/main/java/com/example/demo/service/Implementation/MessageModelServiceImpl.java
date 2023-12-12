package com.example.demo.service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MessageModel;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.MessageModelMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.MessageModelService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageModelServiceImpl implements MessageModelService {
	
	private final MessageModelMapper mapper;

	@Override
	public List<MessageModel> getAllMessageModelWhitStatus(Integer statusId) {
		
		List<MessageModel> messageModel  =  mapper.getAllMessageModelWhitStatus(statusId);
		if (messageModel.isEmpty()) {
			throw new MessageModelNotFoundException("aucun modèle de mail n'existe pour ce status !");
		}
		return messageModel;
	}

}
