package com.example.demo.service;

import com.example.demo.entity.Status;

import java.util.List;

public interface StatusService {
	Status getStatusById(int stId);
	List<Status> getAllStatus();
}
