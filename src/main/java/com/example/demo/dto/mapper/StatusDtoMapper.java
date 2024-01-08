package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.builder.StatusBuilder;
import com.example.demo.builder.dto.StatusDtoBuilder;
import com.example.demo.dto.StatusDto;
import com.example.demo.entity.Status;

@Component
public class StatusDtoMapper {
	public StatusDto statusToDto(Status status) {
		return new StatusDtoBuilder()
				.withStId(status.getStId())
				.withStName(status.getStName())
				.build();
	}

	public Status dtoToStatus(StatusDto statusDto) {
		return new StatusBuilder()
				.withStId(statusDto.getStId())
				.withStName(statusDto.getStName())
				.build();
				
	}
}
