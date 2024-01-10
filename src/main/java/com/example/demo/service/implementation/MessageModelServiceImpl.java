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
	public List<MessageModel> getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(Integer subContractorStatusId,Integer serviceProviderStatusId, Integer subContractorId, Integer serviceProviderId) {
		List<MessageModel> messageModels = messageModelMapper.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorId,serviceProviderId,subContractorStatusId, serviceProviderStatusId);
		return Optional.ofNullable(messageModels).filter(not(List::isEmpty))
				.orElseThrow(() -> new MessageModelNotFoundException("No message model exists for this id!"));
	}
	
	@Override
	public List<MessageModel> getAllMessageModelBySubcontractorId(Integer subContractorId) {
		List<MessageModel> messageModels = messageModelMapper.getAllMessageModelBySubContractorId(subContractorId);
		return Optional.ofNullable(messageModels).filter(not(List::isEmpty))
				.orElseThrow(() -> new MessageModelNotFoundException("No message model exists for this id!"));
	}
}
