package com.example.demo.service;

import org.apache.ibatis.javassist.NotFoundException;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;

public interface GstLogService {
	String saveGstLog(CreateGstLogDTO createGstLogDTO) throws Exception;
	
	GstLogDTO getGstLogByValue(String logValue) throws NotFoundException;
	
	boolean checkResetPasswordExpiration(String logValue) throws NotFoundException;
}
