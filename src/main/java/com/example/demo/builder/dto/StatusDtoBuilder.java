package com.example.demo.builder.dto;

import com.example.demo.dto.StatusDto;

public class StatusDtoBuilder {
	private int stId;
	private String stName;
	private String stDescription;

	public StatusDtoBuilder withStId(int stId) {
		this.stId = stId;
		return this;
	}

	public StatusDtoBuilder withStName(String stName) {
		this.stName = stName;
		return this;
	}

	public StatusDtoBuilder withStDescription(String stDescription) {
		this.stDescription = stDescription;
		return this;
	}

	public StatusDto build() {
		return new StatusDto(stId, stName, stDescription);
	}
}
