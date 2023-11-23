package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.MessageModelDTO;
import com.example.demo.entity.MessageModel;

@Component
public class MessageModelDtoMapper {
	public MessageModelDTO toDto(MessageModel messageModel) {
		return new MessageModelDTO(messageModel.getMmId(), messageModel.getMmType(), messageModel.getMmSubject(), messageModel.getMmBody(),
				messageModel.getMmCreationDate(), messageModel.getMmLastUpdate());
	}

	public MessageModel toMessageModel(MessageModelDTO messageModelDTO) {
		return new MessageModel(messageModelDTO.getMmId(), messageModelDTO.getMmType(), messageModelDTO.getMmSubject(), messageModelDTO.getMmBody(),
				messageModelDTO.getMmCreationDate(), messageModelDTO.getMmLastUpdate());
	}
}
