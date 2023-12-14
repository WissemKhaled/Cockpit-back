package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UUserDTO {
	@JsonProperty("uId")
	private int uId;
	
	@JsonProperty("uEmail")
	private String uEmail;
	
	@JsonProperty("uFirstName")
	private String uFirstName;
	
	@JsonProperty("uLastName")
	private String uLastName;
	
	@JsonProperty("uStatus")
	private boolean uStatus;
	
	@JsonProperty("insertionDate")
	private LocalDateTime insertionDate;
	
	@JsonProperty("lastUpdate")
	private LocalDateTime lastUpdate;
	
	public UUserDTO() {
	}

	public UUserDTO(int uId, String uEmail, String uFirstName, String uLastName, boolean uStatus,
			LocalDateTime insertionDate, LocalDateTime lastUpdate) {
		super();
		this.uId = uId;
		this.uEmail = uEmail;
		this.uFirstName = uFirstName;
		this.uLastName = uLastName;
		this.uStatus = uStatus;
		this.insertionDate = insertionDate;
		this.lastUpdate = lastUpdate;
	}

	public int getUId() {
		return uId;
	}

	public void setUId(int uId) {
		this.uId = uId;
	}

	public String getUEmail() {
		return uEmail;
	}

	public void setUEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getUFirstName() {
		return uFirstName;
	}

	public void setUFirstName(String uFirstName) {
		this.uFirstName = uFirstName;
	}

	public String getULastName() {
		return uLastName;
	}

	public void setULastName(String uLastName) {
		this.uLastName = uLastName;
	}

	public boolean isUStatus() {
		return uStatus;
	}

	public void setUStatus(boolean uStatus) {
		this.uStatus = uStatus;
	}

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
}
