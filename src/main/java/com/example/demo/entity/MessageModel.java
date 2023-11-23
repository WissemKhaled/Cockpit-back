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
public class MessageModel {
	@JsonProperty("mmId")
	private int mmId;
	
	@JsonProperty("mmType")
	private String mmType;
	
	@JsonProperty("mmSubject")
	private String mmSubject;
	
	@JsonProperty("mmModel")
	private String mmModel;
	
	@JsonProperty("mmCreationDate")
	private LocalDateTime mmCreationDate;
	
	@JsonProperty("mmLastUpdate")
	private LocalDateTime mmLastUpdate;
}
