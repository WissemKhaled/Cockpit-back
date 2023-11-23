package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.entity.MessageModel;

@Component
public class CreateMessageModelDtoMapper {
	public CreateMessageModelDTO toDto(MessageModel messageModel) {
		return new CreateMessageModelDTO(messageModel.getMmId(), messageModel.getMmType(), messageModel.getMmSubject(), messageModel.getMmModel(),
				messageModel.getMmCreationDate(), messageModel.getMmLastUpdate());
	}

	public MessageModel toMessageModel(CreateMessageModelDTO createMessageModelDTO) {
		return new MessageModel(createMessageModelDTO.getMmId(), createMessageModelDTO.getMmType(), createMessageModelDTO.getMmSubject(), createMessageModelDTO.getMmModel(),
				createMessageModelDTO.getMmCreationDate(), createMessageModelDTO.getMmLastUpdate());
	}
}
