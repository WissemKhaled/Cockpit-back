package com.example.demo.builder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

public class SubcontractorDtoBuilder {
	private int sId;
	private String sName;
	private String sEmail;
	private LocalDateTime sCreationDate;
	private LocalDateTime sLastUpdateDate;
	private Status status;
	private List<Integer> serviceProvidersIds;

	public SubcontractorDtoBuilder withSId(int sId) {
		this.sId = sId;
		return this;
	}

	public SubcontractorDtoBuilder withSName(String sName) {
		this.sName = sName;
		return this;
	}

	public SubcontractorDtoBuilder withSEmail(String sEmail) {
		this.sEmail = sEmail;
		return this;
	}

	public SubcontractorDtoBuilder withSCreationDate(LocalDateTime sCreationDate) {
		this.sCreationDate = sCreationDate;
		return this;
	}

	public SubcontractorDtoBuilder withSLastUpdateDate(LocalDateTime sLastUpdateDate) {
		this.sLastUpdateDate = sLastUpdateDate;
		return this;
	}

	public SubcontractorDtoBuilder withStatus(Status status) {
		this.status = status;
		return this;
	}

	public SubcontractorDtoBuilder withServiceProvidersIds(List<Integer> serviceProvidersIds) {
		this.serviceProvidersIds = serviceProvidersIds;
		return this;
	}

	public SubcontractorDto build() {
		return new SubcontractorDto(sId, sName, sEmail, sCreationDate, sLastUpdateDate, status, serviceProvidersIds);
	}

}
