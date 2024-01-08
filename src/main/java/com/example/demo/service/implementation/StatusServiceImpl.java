package com.example.demo.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.StatusDto;
import com.example.demo.dto.mapper.StatusDtoMapper;
import com.example.demo.entity.Status;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.StatusMapper;
import com.example.demo.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

	private final StatusMapper statusMapper;
	private final StatusDtoMapper statusDtoMapper;

	public StatusServiceImpl(StatusMapper statusMapper, StatusDtoMapper statusDtoMapper) {
		this.statusMapper = statusMapper;
		this.statusDtoMapper = statusDtoMapper;
	}

	@Override
	public Status getStatusById(int stId) {
		return statusMapper.findStatusById(stId);
	}

	@Override
	public List<StatusDto> getAllStatus() {
		Optional<List<Status>> optionalStatusList = Optional.ofNullable(statusMapper.getAllStatus());
		if (optionalStatusList.isEmpty()) {
			throw new EntityNotFoundException("Il n'y a pas de statut enregistré");
		}
		return optionalStatusList.get().stream().map(statusDtoMapper::statusToDto).toList();
	}

}
