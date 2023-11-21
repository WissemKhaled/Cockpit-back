package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceProviderDto {
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
	
	@JsonProperty("status")
	private Status status;

	@JsonProperty("subcontractor")
	private Subcontractor subcontractor;
}
