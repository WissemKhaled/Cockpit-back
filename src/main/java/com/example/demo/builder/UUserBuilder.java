package com.example.demo.builder;

import java.time.LocalDateTime;

import com.example.demo.entity.UUser;

public class UUserBuilder {
	private int uId;
	private String uEmail;
	private String uPassword;
	private String uFirstName;
	private String uLastName;
	private boolean uStatus;
	private LocalDateTime uInsertionDate;
	private LocalDateTime uLastUpdate;

	public UUserBuilder withUId(int uId) {
		this.uId = uId;
		return this;
	}

	public UUserBuilder withUEmail(String uEmail) {
		this.uEmail = uEmail;
		return this;
	}

	public UUserBuilder withUPassword(String uPassword) {
		this.uPassword = uPassword;
		return this;
	}

	public UUserBuilder withUFirstName(String uFirstName) {
		this.uFirstName = uFirstName;
		return this;
	}

	public UUserBuilder withULastName(String uLastName) {
		this.uLastName = uLastName;
		return this;
	}

	public UUserBuilder withUStatus(boolean uStatus) {
		this.uStatus = uStatus;
		return this;
	}

	public UUserBuilder withUInsertionDate(LocalDateTime uInsertionDate) {
		this.uInsertionDate = uInsertionDate;
		return this;
	}

	public UUserBuilder withULastUpdate(LocalDateTime uLastUpdate) {
		this.uLastUpdate = uLastUpdate;
		return this;
	}

	public UUser build() {
		return new UUser(uId, uEmail, uPassword, uFirstName, uLastName, uStatus, uInsertionDate, uLastUpdate);
	}

}
