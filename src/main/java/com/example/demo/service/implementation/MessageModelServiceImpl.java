package com.example.demo.service.implementation;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.MessageModelMapper;
import com.example.demo.service.MessageModelService;
import static java.util.function.Predicate.not;

@Service
public class MessageModelServiceImpl implements MessageModelService {

	private final MessageModelMapper messageModelMapper;

	public MessageModelServiceImpl(MessageModelMapper mapper) {
		this.messageModelMapper = mapper;
	}

	
	@Override
	public List<MessageModel> getAllMessageModel() {

		List<MessageModel> messageModel = messageModelMapper.getAllMessageModel();
		if (messageModel.isEmpty()) {
			throw new MessageModelNotFoundException("aucun modèle de mail n'existe pour ce status !");
		}
		return messageModel;
	}
	@Override
	public List<MessageModel> getAllMessageModelsAndStatusByServiceProviderId(Integer serviceproviderId) {
		
		List<MessageModel> messageModels = messageModelMapper.getMessageModelsAndStatusByServiceProviderId(serviceproviderId);

		if (messageModels.isEmpty()) {
			throw new MessageModelNotFoundException("aucun modèle de mail n'existe pour ce status !");
		}
		return messageModels;
	}

	@Override
	public List<MessageModel> getAllMessageModelsAndStatusBySubcontractorCategoryAndId(Integer subcontractorId) {
		List<MessageModel> messageModels = messageModelMapper.getMessageModelsAndStatusBySubcontractorCategoryAndId(subcontractorId);
		return Optional.ofNullable(messageModels).filter(not(List::isEmpty))
				.orElseThrow(() -> new MessageModelNotFoundException("No message model exists for this subcontractor id!"));
	}

}
