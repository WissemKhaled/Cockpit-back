package com.example.demo.service.Implementation;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Status;
import com.example.demo.mappers.StatusMapper;
import com.example.demo.service.StatusService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

	private final StatusMapper statusMapper;

	@Override
	public Status getStatusById(int stId) {
		return statusMapper.findStatusById(stId);
	}

}
