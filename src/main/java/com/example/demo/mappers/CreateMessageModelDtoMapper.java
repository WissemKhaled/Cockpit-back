package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.entity.MessageModel;

@Component
public class CreateMessageModelDtoMapper {
	public CreateMessageModelDTO toDto(MessageModel messageModel) {
		return new CreateMessageModelDTO(messageModel.getMmId(), messageModel.getMmType(), messageModel.getMmSubject(), messageModel.getMmBody(),
				messageModel.getMmCreationDate(), messageModel.getMmLastUpdate());
	}

	public MessageModel toMessageModel(CreateMessageModelDTO createMessageModelDTO) {
		return new MessageModel(createMessageModelDTO.getMmId(), createMessageModelDTO.getMmType(), createMessageModelDTO.getMmSubject(), createMessageModelDTO.getMmBody(),
				createMessageModelDTO.getMmCreationDate(), createMessageModelDTO.getMmLastUpdate());
	}
}
