package com.example.demo.builder;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

public class ServiceProviderBuilder {
	private int spId;
	private String spFirstName;
	private String spName;
	private String spEmail;
	private LocalDateTime spCreationDate;
	private LocalDateTime spLastUpdateDate;
	private Subcontractor subcontractor;
	private Status spStatus;

	public ServiceProviderBuilder withSpId(int spId) {
		this.spId = spId;
		return this;
	}

	public ServiceProviderBuilder withSpFirstName(String spFirstName) {
		this.spFirstName = spFirstName;
		return this;
	}

	public ServiceProviderBuilder withSpName(String spName) {
		this.spName = spName;
		return this;
	}

	public ServiceProviderBuilder withSpEmail(String spEmail) {
		this.spEmail = spEmail;
		return this;
	}

	public ServiceProviderBuilder withSpCreationDate(LocalDateTime spCreationDate) {
		this.spCreationDate = spCreationDate;
		return this;
	}

	public ServiceProviderBuilder withSpLastUpdateDate(LocalDateTime spLastUpdateDate) {
		this.spLastUpdateDate = spLastUpdateDate;
		return this;
	}

	public ServiceProviderBuilder withSubcontractor(Subcontractor subcontractor) {
		this.subcontractor = subcontractor;
		return this;
	}

	public ServiceProviderBuilder withSpStatus(Status spStatus) {
		this.spStatus = spStatus;
		return this;
	}

	public ServiceProvider build() {
		return new ServiceProvider(spId, spFirstName, spName, spEmail, spCreationDate, spLastUpdateDate, subcontractor,
				spStatus);
	}

}
