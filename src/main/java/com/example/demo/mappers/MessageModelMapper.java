package com.example.demo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {
	@Insert("INSERT INTO gst_message_model(mm_type, mm_subject, mm_model, mm_creation_date, mm_last_update) VALUES (#{mmType}, #{mmSubject}, #{mmModel}, #{mmCreationDate}, #{mmLastUpdate})")
	@Options(useGeneratedKeys = true, keyProperty = "mmId", keyColumn = "mm_id")
	int insertMessageModel(MessageModel messageModel);
}
