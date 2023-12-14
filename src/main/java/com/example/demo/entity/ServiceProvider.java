package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceProvider {
	@JsonProperty("spId")
	private int spId;

	@JsonProperty("spFirstName")
	private String spFirstName;

	@JsonProperty("spName")
	private String spName;

	@JsonProperty("spEmail")
	private String spEmail;

	@JsonProperty("spCreationDate")
	private LocalDateTime spCreationDate;

	@JsonProperty("spLastUpdateDate")
	private LocalDateTime spLastUpdateDate;

	@JsonProperty("subcontractor")
	private Subcontractor subcontractor;

	@JsonProperty("spStatus")
	private Status spStatus;

	public ServiceProvider(int spId) {
		this.spId = spId;
	}

	public ServiceProvider() {
	}

	public ServiceProvider(int spId, String spFirstName, String spName, String spEmail, LocalDateTime spCreationDate,
			LocalDateTime spLastUpdateDate, Subcontractor subcontractor, Status spStatus) {
		this.spId = spId;
		this.spFirstName = spFirstName;
		this.spName = spName;
		this.spEmail = spEmail;
		this.spCreationDate = spCreationDate;
		this.spLastUpdateDate = spLastUpdateDate;
		this.subcontractor = subcontractor;
		this.spStatus = spStatus;
	}

	public int getSpId() {
		return spId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public String getSpFirstName() {
		return spFirstName;
	}

	public void setSpFirstName(String spFirstName) {
		this.spFirstName = spFirstName;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getSpEmail() {
		return spEmail;
	}

	public void setSpEmail(String spEmail) {
		this.spEmail = spEmail;
	}

	public LocalDateTime getSpCreationDate() {
		return spCreationDate;
	}

	public void setSpCreationDate(LocalDateTime spCreationDate) {
		this.spCreationDate = spCreationDate;
	}

	public LocalDateTime getSpLastUpdateDate() {
		return spLastUpdateDate;
	}

	public void setSpLastUpdateDate(LocalDateTime spLastUpdateDate) {
		this.spLastUpdateDate = spLastUpdateDate;
	}

	public Subcontractor getSubcontractor() {
		return subcontractor;
	}

	public void setSubcontractor(Subcontractor subcontractor) {
		this.subcontractor = subcontractor;
	}

	public Status getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(Status spStatus) {
		this.spStatus = spStatus;
	}

	@Override
	public String toString() {
		return "ServiceProvider [spId=" + spId + ", spFirstName=" + spFirstName + ", spName=" + spName + ", spEmail="
				+ spEmail + ", spCreationDate=" + spCreationDate + ", spLastUpdateDate=" + spLastUpdateDate
				+ ", subcontractor=" + subcontractor + ", spStatus=" + spStatus + "]";
	}

}
