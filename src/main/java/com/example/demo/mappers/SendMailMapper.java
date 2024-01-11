package com.example.demo.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.SendMailDTO;

@Mapper
public interface SendMailMapper {

	void saveSendMail(SendMailDTO message_send);

}
  