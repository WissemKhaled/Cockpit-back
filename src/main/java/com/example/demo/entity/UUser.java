package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UUser {
	@JsonProperty("uId")
	private int uId;
	@JsonProperty("uEmail")
	private String uEmail;
	@JsonProperty("uPassword")
	private String uPassword;
	@JsonProperty("uFirstName")
	private String uFirstName;
	@JsonProperty("uLastName")
	private String uLastName;
	@JsonProperty("uStatus")
	private boolean uStatus;
	@JsonProperty("uInsertionDate")
	private LocalDateTime uInsertionDate;
	@JsonProperty("uLastUpdate")
	private LocalDateTime uLastUpdate;

	public UUser() {
	}

	public UUser(int uId, String uEmail, String uFirstName, String uLastName, boolean uStatus,
			LocalDateTime uInsertionDate, LocalDateTime uLastUpdate) {
		this.uId = uId;
		this.uEmail = uEmail;
		this.uFirstName = uFirstName;
		this.uLastName = uLastName;
		this.uStatus = uStatus;
		this.uInsertionDate = uInsertionDate;
		this.uLastUpdate = uLastUpdate;
	}

	public UUser(int uId, String uEmail, String uPassword, String uFirstName, String uLastName, boolean uStatus,
			LocalDateTime uInsertionDate, LocalDateTime uLastUpdate) {
		this.uId = uId;
		this.uEmail = uEmail;
		this.uPassword = uPassword;
		this.uFirstName = uFirstName;
		this.uLastName = uLastName;
		this.uStatus = uStatus;
		this.uInsertionDate = uInsertionDate;
		this.uLastUpdate = uLastUpdate;
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

	public String getUPassword() {
		return uPassword;
	}

	public void setUPassword(String uPassword) {
		this.uPassword = uPassword;
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

	public LocalDateTime getUInsertionDate() {
		return uInsertionDate;
	}

	public void setUInsertionDate(LocalDateTime uInsertionDate) {
		this.uInsertionDate = uInsertionDate;
	}

	public LocalDateTime getULastUpdate() {
		return uLastUpdate;
	}

	public void setULastUpdate(LocalDateTime uLastUpdate) {
		this.uLastUpdate = uLastUpdate;
	}

}
