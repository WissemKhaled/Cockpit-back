package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	@JsonProperty("uInsertionDate")
	private LocalDateTime uInsertionDate;
	@JsonProperty("uLastUpdate")
	private LocalDateTime uLastUpdate;
	
	public UUser(int uId, String uEmail, String uFirstName, String uLastName, boolean uStatus, LocalDateTime uInsertionDate, LocalDateTime uLastUpdate) {
		this.uId = uId;
		this.uEmail = uEmail;
		this.uFirstName = uFirstName;
		this.uLastName = uLastName;
		this.uStatus = uStatus;
		this.uInsertionDate = uInsertionDate;
		this.uLastUpdate = uLastUpdate;
	}
}
