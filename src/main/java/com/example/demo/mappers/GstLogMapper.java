package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.GstLog;

@Mapper
public interface GstLogMapper {
//	@Insert("INSERT INTO gst_log(log_type, log_email, log_value, log_creation_date) VALUES (#{logType}, #{logEmail}, #{logValue}, #{logCreationDate})")
//	@Options(useGeneratedKeys = true, keyProperty = "logId", keyColumn = "log_id")
	int insertLog(GstLog gstLog);
	
	GstLog getLogByValue(String logValue);
	
	List<GstLog> getThreeLatestLogs(@Param("email") String email);
	
	int updateGstLogPwd(@Param("gstLog") GstLog gstLog);
}
