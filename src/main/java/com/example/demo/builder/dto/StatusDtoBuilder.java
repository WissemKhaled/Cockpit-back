package com.example.demo.builder.dto;

import com.example.demo.dto.StatusDto;

public class StatusDtoBuilder {
	private int stId;
	private String stName;

	public StatusDtoBuilder withStId(int stId) {
		this.stId = stId;
		return this;
	}

	public StatusDtoBuilder withStName(String stName) {
		this.stName = stName;
		return this;
	}

	public StatusDto build() {
		return new StatusDto(stId, stName);
	}
}
