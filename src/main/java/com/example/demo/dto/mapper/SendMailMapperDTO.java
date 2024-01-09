package com.example.demo.dto.mapper;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.SendMail;

public class SendMailMapperDTO {
	

	public SendMailDTO toDto(SendMail sendMail) {
		return new SendMailDTO(sendMail.getMsId(), sendMail.getMsTo(), sendMail.getMsCc(), sendMail.getMsSubject(),
				sendMail.getMsBody(), sendMail.getMsSendDate(), sendMail.getMsFkUserId());

	}

	public SendMail toSendMail(SendMailDTO dto) {
		return new SendMail(dto.getMsId(), dto.getMsTo(), dto.getMsCc(), dto.getMsSubject(), dto.getMsBody(),
				dto.getMsSendDate(), dto.getMsFkUserId());


	}

}
