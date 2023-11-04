package com.example.demo.dto;

import java.time.LocalDateTime;

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
	
	@JsonProperty("insertionDate")
	private LocalDateTime insertionDate;
	
	@JsonProperty("lastUpdate")
	private LocalDateTime lastUpdate;
}
