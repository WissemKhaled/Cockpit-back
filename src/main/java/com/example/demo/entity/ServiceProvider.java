package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

}
