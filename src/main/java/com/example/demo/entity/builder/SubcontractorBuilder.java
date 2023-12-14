package com.example.demo.entity.builder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

public class SubcontractorBuilder {
	private int sId;
	private String sName;
	private String sEmail;
	private LocalDateTime sCreationDate;
	private LocalDateTime sLastUpdateDate;
	private Status status;
	private List<ServiceProvider> serviceProviders;

	public SubcontractorBuilder withSId(int sId) {
		this.sId = sId;
		return this;
	}

	public SubcontractorBuilder withSName(String sName) {
		this.sName = sName;
		return this;
	}

	public SubcontractorBuilder withSEmail(String sEmail) {
		this.sEmail = sEmail;
		return this;
	}

	public SubcontractorBuilder withSCreationDate(LocalDateTime sCreationDate) {
		this.sCreationDate = sCreationDate;
		return this;
	}

	public SubcontractorBuilder withSLastUpdateDate(LocalDateTime sLastUpdateDate) {
		this.sLastUpdateDate = sLastUpdateDate;
		return this;
	}

	public SubcontractorBuilder withStatus(Status status) {
		this.status = status;
		return this;
	}

	public SubcontractorBuilder withServiceProviders(List<ServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
		return this;
	}

	public Subcontractor build() {
		return new Subcontractor(sId, sName, sEmail, sCreationDate, sLastUpdateDate, status, serviceProviders);
	}

	public static void main(String[] args) {
		 Subcontractor subcontractor = new SubcontractorBuilder()
	                .withSId(1)
	                .withSName("ABC Subcontractors")
	                .withSEmail("abc@example.com")
	                .withSCreationDate(LocalDateTime.now())
	                .withSLastUpdateDate(LocalDateTime.now())
	                .withStatus(new Status(1))
	                .withServiceProviders(Arrays.asList(new ServiceProvider(1),new ServiceProvider(2)))
	                .build();
	}
}
