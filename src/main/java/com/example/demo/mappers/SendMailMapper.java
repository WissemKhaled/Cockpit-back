package com.example.demo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.entity.SendMail;
import com.example.demo.entity.Subcontractor;

@Mapper
public interface SendMailMapper {

	
	
	@Insert("INSERT INTO message_send (ms_sender, ms_recipient, ms_creation_date, ms_fk_model_email_id) "
			+ "VALUES (#{msSender}, #{msRecipient}, #{msCreationsDate}, #{messageModel.mmId})")
	@Options(useGeneratedKeys = true, keyProperty = "msId", keyColumn = "ms_id")
	@Result(property = "msId", column = "ms_id")
	@Result(property = "msSender", column = "ms_sender")
	@Result(property = "msRecipient", column = "ms_recipient")
	@Result(property = "msCreationsDate", column = "ms_creation_date")
	@Result(property = "messageModel.mmId", column = "ms_fk_model_email_id")
	void saveMailAndSend(SendMail message_send); 
}
