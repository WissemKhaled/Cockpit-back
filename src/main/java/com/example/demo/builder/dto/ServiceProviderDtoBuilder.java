package com.example.demo.builder.dto;

import java.time.LocalDateTime;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.Status;

public class ServiceProviderDtoBuilder {
	private int spId;
	private String spFirstName;
	private String spName;
	private String spEmail;
	private LocalDateTime spCreationDate;
	private LocalDateTime spLastUpdateDate;
	private String subcontractorName;
	private Status spStatus;

	public ServiceProviderDtoBuilder withSpId(int spId) {
		this.spId = spId;
		return this;
	}

	public ServiceProviderDtoBuilder withSpFirstName(String spFirstName) {
		this.spFirstName = spFirstName;
		return this;
	}

	public ServiceProviderDtoBuilder withSpName(String spName) {
		this.spName = spName;
		return this;
	}

	public ServiceProviderDtoBuilder withSpEmail(String spEmail) {
		this.spEmail = spEmail;
		return this;
	}

	public ServiceProviderDtoBuilder withSpCreationDate(LocalDateTime spCreationDate) {
		this.spCreationDate = spCreationDate;
		return this;
	}

	public ServiceProviderDtoBuilder withSpLastUpdateDate(LocalDateTime spLastUpdateDate) {
		this.spLastUpdateDate = spLastUpdateDate;
		return this;
	}

	public ServiceProviderDtoBuilder withSubcontractor(String subcontractorName) {
		this.subcontractorName = subcontractorName;
		return this;
	}

	public ServiceProviderDtoBuilder withSpStatus(Status spStatus) {
		this.spStatus = spStatus;
		return this;
	}

	public ServiceProviderDto build() {
		return new ServiceProviderDto(spId, spFirstName, spName, spEmail, spCreationDate, spLastUpdateDate, spStatus,
				subcontractorName);
	}
}
