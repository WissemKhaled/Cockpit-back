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
public class GstLog {
	@JsonProperty("logId")
	private int logId;
	
	@JsonProperty("logType")
	private String logType;
	
	@JsonProperty("logEmail")
	private String logEmail;
	
	@JsonProperty("logCreationDate")
	private LocalDateTime logCreationDate;
	
	@JsonProperty("mmLastUpdate")
	private LocalDateTime logLastUpdate;
	
}
