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
public class MessageModelDTO {
	@JsonProperty("mmId")
	private int mmId;
	
	@JsonProperty("mmType")
	private String mmType;
	
	@JsonProperty("mmSubject")
	private String mmSubject;
	
	@JsonProperty("mmBody")
	private String mmBody;
	
	@JsonProperty("mmCreationDate")
	private LocalDateTime mmCreationDate;
	
	@JsonProperty("mmLastUpdate")
	private LocalDateTime mmLastUpdate;
}
