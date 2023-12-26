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
	@Insert("INSERT INTO gst_log(log_type, log_email, log_value, log_creation_date) VALUES (#{logType}, #{logEmail}, #{logValue}, #{logCreationDate})")
	@Options(useGeneratedKeys = true, keyProperty = "logId", keyColumn = "log_id")
	int insertLog(GstLog gstLog);
	
	@Select("SELECT * FROM gst_log "
			+ "WHERE log_value = #{logValue}")
	@Result(property = "logId", column = "log_id")
	@Result(property = "logType", column = "log_type")
	@Result(property = "logEmail", column = "log_email")
	@Result(property = "logPassword", column = "log_password")
	@Result(property = "logValue", column = "log_value")
	@Result(property = "logCreationDate", column = "log_creation_date")
	@Result(property = "logLastUpdate", column = "log_last_update")
	GstLog getLogByValue(String logValue);
	
	@Select("SELECT * FROM gst_log "
	        + "WHERE log_email = #{email} "
	        + "AND log_password IS NOT NULL "
	        + "ORDER BY log_id DESC "
	        + "LIMIT 3")
	@Result(property = "logId", column = "log_id")
	@Result(property = "logType", column = "log_type")
	@Result(property = "logEmail", column = "log_email")
	@Result(property = "logPassword", column = "log_password")
	@Result(property = "logValue", column = "log_value")
	@Result(property = "logCreationDate", column = "log_creation_date")
	@Result(property = "logLastUpdate", column = "log_last_update")
	List<GstLog> getThreeLatestLogs(@Param("email") String email);
	
	@Update("UPDATE gst_log SET log_password = #{gstLog.logPassword}, log_last_update = #{gstLog.logLastUpdate} WHERE log_value = #{gstLog.logValue}")
	int updateGstLogPwd(@Param("gstLog") GstLog gstLog);
}
