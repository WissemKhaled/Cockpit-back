package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.MessageModel;

public interface MessageModelService {
	
	
	List<MessageModel> getAllMessageModelWhitStatus(Integer statusId);

}
