package com.example.demo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.GstLog;

@Mapper
public interface GstLogMapper {
	@Insert("INSERT INTO gst_log(log_type, log_email, log_value, log_creation_date) VALUES (#{logType}, #{logEmail}, #{logValue}, #{logCreationDate})")
	@Options(useGeneratedKeys = true, keyProperty = "logId", keyColumn = "log_id")
	int insertLog(GstLog gstLog);
	
	@Select("SELECT * FROM gst_log "
			+ "WHERE log_value = #{logValue}")
	@Result(property = "logId", column = "log_id")
	@Result(property = "logType", column = "log_type")
	@Result(property = "logEmail", column = "log_email")
	@Result(property = "logValue", column = "log_value")
	@Result(property = "logCreationDate", column = "log_creation_date")
	GstLog getLogByValue(String logValue);
}
