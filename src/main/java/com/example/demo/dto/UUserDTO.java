package com.example.demo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
	
	@JsonProperty("uRoles")
	private String uRoles;
	
	@JsonProperty("insertionDate")
	private LocalDate insertionDate;
	
	@JsonProperty("lastUpdate")
	private LocalDate lastUpdate;
	
	public UUserDTO(int uId, String uEmail, String uFirstName, String uLastName, boolean uStatus, String uRoles) {
		this.uId = uId;
		this.uEmail = uEmail;
		this.uFirstName = uFirstName;
		this.uLastName = uLastName;
		this.uStatus = uStatus;
		this.uRoles = uRoles;
	}
}
