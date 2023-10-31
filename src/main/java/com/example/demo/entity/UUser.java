package com.example.demo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
	@JsonProperty("uRoles")
	private String uRoles;
	@JsonProperty("uInsertionDate")
	private LocalDate uInsertionDate;
	@JsonProperty("uLastUpdate")
	private LocalDate uLastUpdate;
	
	public UUser(int uId, String uEmail, String uFirstName, String uLastName, boolean uStatus, LocalDate uInsertionDate, LocalDate uLastUpdate, String uRoles) {
		this.uId = uId;
		this.uEmail = uEmail;
		this.uFirstName = uFirstName;
		this.uLastName = uLastName;
		this.uStatus = uStatus;
		this.uInsertionDate = uInsertionDate;
		this.uLastUpdate = uLastUpdate;
		this.uRoles = uRoles;
	}
}
