package com.example.demo.service;

import org.apache.ibatis.javassist.NotFoundException;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.exception.GeneralException;

public interface GstLogService {
	String saveGstLog(CreateGstLogDTO createGstLogDTO) throws GeneralException, NotFoundException;
	
	GstLogDTO getGstLogByValue(String logValue) throws NotFoundException;
	
	boolean checkResetPasswordExpiration(String logValue) throws NotFoundException;
}
