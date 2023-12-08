package com.example.demo.dto;

import com.example.demo.entity.SendMail;
import com.example.demo.entity.UUser;

public class SendMailMapperDTO {

	public SendMailDTO toDto(SendMail sendMail) {
		return new SendMailDTO(sendMail.getMsId(), sendMail.getMsSender(), sendMail.getMsTo(), sendMail.getMsCc(),sendMail.getMsSubject(),
				sendMail.getMsBody(), sendMail.getMsError(), sendMail.getMsStatus(), sendMail.getMsCreationsDate());

	}

	public SendMail toSendMail(SendMailDTO dto) {
		return new SendMail(dto.getMsId(), dto.getMsSender(), dto.getMsTo(), dto.getMsCc(),dto.getMsSubject(),
				dto.getMsBody(), dto.getMsError(), dto.getMsStatus(), dto.getMsCreationsDate());
	}

}
