package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subcontractor {

	@JsonProperty("sId")
	private int sId;

	@JsonProperty("sName")
	private String sName;

	@JsonProperty("sEmail")
	private String sEmail;

	@JsonProperty("sCreationDate")
	private LocalDateTime sCreationDate;

	@JsonProperty("sLastUpdateDate")
	private LocalDateTime sLastUpdateDate;

	@JsonProperty("status")
	private Status status;

	@JsonProperty("serviceProviders")
	private List<ServiceProvider> serviceProviders;

	public Subcontractor() {
	}

	public Subcontractor(int sId) {
		this.sId = sId;
	}

	public Subcontractor(String sName, String sEmail) {
		this.sName = sName;
		this.sEmail = sEmail;
	}

	public Subcontractor(int sId, String sName, String sEmail, LocalDateTime sCreationDate,
			LocalDateTime sLastUpdateDate, Status status, List<ServiceProvider> serviceProviders) {
		super();
		this.sId = sId;
		this.sName = sName;
		this.sEmail = sEmail;
		this.sCreationDate = sCreationDate;
		this.sLastUpdateDate = sLastUpdateDate;
		this.status = status;
		this.serviceProviders = serviceProviders;
	}

	public int getSId() {
		return sId;
	}

	public void setSId(int sId) {
		this.sId = sId;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String sName) {
		this.sName = sName;
	}

	public String getSEmail() {
		return sEmail;
	}

	public void setSEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public LocalDateTime getSCreationDate() {
		return sCreationDate;
	}

	public void setSCreationDate(LocalDateTime sCreationDate) {
		this.sCreationDate = sCreationDate;
	}

	public LocalDateTime getSLastUpdateDate() {
		return sLastUpdateDate;
	}

	public void setSLastUpdateDate(LocalDateTime sLastUpdateDate) {
		this.sLastUpdateDate = sLastUpdateDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<ServiceProvider> getServiceProviders() {
		return serviceProviders;
	}

	public void setServiceProviders(List<ServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	@Override
	public String toString() {
		return "Subcontractor [sId=" + sId + ", sName=" + sName + ", sEmail=" + sEmail + ", sCreationDate="
				+ sCreationDate + ", sLastUpdateDate=" + sLastUpdateDate + ", status=" + status + ", serviceProviders="
				+ serviceProviders + "]";
	}

}