package com.example.demo.service.implementation;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Optional;

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
	public List<MessageModel> getAllMessageModelBySubcontractorId(Integer subContractorId) {
		List<MessageModel> messageModels = messageModelMapper.getAllMessageModelBySubcontractorId(subContractorId);
		return Optional.ofNullable(messageModels).filter(not(List::isEmpty))
				.orElseThrow(() -> new MessageModelNotFoundException("No message model exists for this id!"));
	}
	
	@Override
	public List<MessageModel> getAllMessageModelByServiceProviderId(Integer serviceProviderId) {
		List<MessageModel> messageModels = messageModelMapper.getAllMessageModelByServiceProviderId(serviceProviderId);
		return Optional.ofNullable(messageModels).filter(not(List::isEmpty))
				.orElseThrow(() -> new MessageModelNotFoundException("No message model exists for this id!"));
	}
}
