package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {
	@Insert("INSERT INTO gst_message_model(mm_type, mm_subject, mm_body, mm_creation_date, mm_last_update) VALUES (#{mmType}, #{mmSubject}, #{mmBody}, #{mmCreationDate}, #{mmLastUpdate})")
	@Options(useGeneratedKeys = true, keyProperty = "mmId", keyColumn = "mm_id")
	int insertMessageModel(MessageModel messageModel);
	
	@Select("SELECT * FROM gst_message_model "
	        + "WHERE mm_id = #{mmId}")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmCreationDate", column = "mm_creation_date")
	@Result(property = "mmLastUpdate", column = "mm_last_update")
	MessageModel getMessageModelById(int mmId);
	
	@Select("SELECT * FROM gst_message_model "
			+ "WHERE mm_type = #{mmType}")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmCreationDate", column = "mm_creation_date")
	@Result(property = "mmLastUpdate", column = "mm_last_update")
	List<MessageModel> getMessageModelByType(String mmType);
	
	@Update("UPDATE gst_message_model "
	        + "SET mm_type = #{mmType}, mm_subject = #{mmSubject}, mm_body = #{mmBody}, mm_last_update = #{mmLastUpdate} "
	        + "WHERE mm_id = #{mmId}")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmLastUpdate", column = "mm_last_update")
	int updateMessageModel(MessageModel messageModel);
}
