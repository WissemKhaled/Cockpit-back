package com.example.demo.dto;

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
public class GstLogDTO {
	@JsonProperty("logId")
	private int logId;
	
	@JsonProperty("logType")
	private String logType;
	
	@JsonProperty("logEmail")
	private String logEmail;
	
	@JsonProperty("logValue")
	private String logValue;
	
	@JsonProperty("logCreationDate")
	private LocalDateTime logCreationDate;
}
