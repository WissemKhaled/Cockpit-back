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

	
	
	@Insert("INSERT INTO message_send (ms_sender, ms_to, ms_cc, ms_subject, ms_body, ms_error, ms_status, ms_creation_date, ms_fk_model_email_id) "
			+ "VALUES (#{msSender}, #{msTo}, #{msCc}, #{msSubject}, #{msBody}, #{msError}, #{msStatus}, #{msCreationsDate}, #{messageModel.mmId})")
	@Options(useGeneratedKeys = true, keyProperty = "msId", keyColumn = "ms_id")
	@Result(property = "msId", column = "ms_id")
	@Result(property = "msSender", column = "ms_sender")
	@Result(property = "msTo", column = "ms_to")
	@Result(property = "msCc", column = "ms_cc")
	@Result(property = "msSubject", column = "ms_subject")
	@Result(property = "msBody", column = "ms_body")
	@Result(property = "msError", column = "ms_error")
	@Result(property = "msStatus", column = "ms_status")
	@Result(property = "msCreationsDate", column = "ms_creation_date")
	@Result(property = "messageModel.mmId", column = "ms_fk_model_email_id")
	void saveMailAndSend(SendMail message_send); 
}
