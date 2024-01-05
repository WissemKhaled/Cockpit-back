package com.example.demo.dto.mapper;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.SendMail;
import com.example.demo.entity.UUser;
import com.example.demo.service.UserInfoService;

public class SendMailMapperDTO {
	
	private UserInfoService infoService;

	public SendMailDTO toDto(SendMail sendMail) {
		return new SendMailDTO(sendMail.getMsId(), sendMail.getMsTo(), sendMail.getMsCc(),sendMail.getMsSubject(),
				sendMail.getMsBody(), sendMail.getMsSendDate() , sendMail.getUser());

	}

	public SendMail toSendMail(SendMailDTO dto) {
		return new SendMail(dto.getMsId(), dto.getMsTo(), dto.getMsCc(),dto.getMsSubject(),
				dto.getMsBody(), dto.getMsSendDate() , dto.getUser());

	}

}
