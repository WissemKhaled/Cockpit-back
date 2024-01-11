package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StatusDto;
import com.example.demo.entity.Status;

public interface StatusService {
	Status getStatusById(int stId);

	List<StatusDto> getAllStatus();
}
