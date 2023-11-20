package com.example.demo.entity;

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

	@JsonProperty("status")
	private Status status;
}
