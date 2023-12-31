package com.example.demo.service.implementation;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Status;
import com.example.demo.mappers.StatusMapper;
import com.example.demo.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

	private final StatusMapper statusMapper;
	
	public StatusServiceImpl(StatusMapper statusMapper) {
		this.statusMapper = statusMapper;
	}
	
	@Override
	public Status getStatusById(int stId) {
		return statusMapper.findStatusById(stId);
	}

}
