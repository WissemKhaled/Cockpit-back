package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.GstLog;

@Mapper
public interface GstLogMapper {
//	@Options(useGeneratedKeys = true, keyProperty = "logId", keyColumn = "log_id")
	int insertLog(GstLog gstLog);
	
	GstLog getLogByValue(String logValue);
	
	List<GstLog> getThreeLatestLogs(String email);
	
	int updateGstLogPwd(GstLog gstLog);
}
