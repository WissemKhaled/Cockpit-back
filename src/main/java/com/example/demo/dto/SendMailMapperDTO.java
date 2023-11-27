package com.example.demo.dto;

import com.example.demo.entity.SendMail;
import com.example.demo.entity.UUser;

public class SendMailMapperDTO {

	public SendMailDTO toDto(SendMail sendMail) {
		return new SendMailDTO(sendMail.getMsId(), sendMail.getMsSender(), sendMail.getMsRecipient(),
				sendMail.getMsCreationsDate(), sendMail.getMessageModel());

	}

	public SendMail toSendMail(SendMailDTO dto) {
		return new SendMail(dto.getMsId(), dto.getMsSender(), dto.getMsRecipient(), dto.getMsCreationsDate(),
				dto.getMessageModel());
	}

}
