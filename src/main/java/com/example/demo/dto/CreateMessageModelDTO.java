package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;

public class CreateMessageModelDTO {
	@JsonProperty("mmId")
	private int mmId;
	
	@NotEmpty(message = "Le type de message ne doit pas être vide")
	@JsonProperty("mmType")
	private String mmType;
	
	@NotEmpty(message = "Le sujet du message ne doit pas être vide")
	@JsonProperty("mmSubject")
	private String mmSubject;
	
	@NotEmpty(message = "Le corps du message ne doit pas être vide")
	@JsonProperty("mmBody")
	private String mmBody;
	
	@JsonProperty("mmCreationDate")
	private LocalDateTime mmCreationDate;
	
	@JsonProperty("mmLastUpdate")
	private LocalDateTime mmLastUpdate;
	
	public CreateMessageModelDTO() {
	}

	public CreateMessageModelDTO(int mmId, String mmType, String mmSubject, String mmBody, LocalDateTime mmCreationDate,
			LocalDateTime mmLastUpdate) {
		this.mmId = mmId;
		this.mmType = mmType;
		this.mmSubject = mmSubject;
		this.mmBody = mmBody;
		this.mmCreationDate = mmCreationDate;
		this.mmLastUpdate = mmLastUpdate;
	}

	public int getMmId() {
		return mmId;
	}

	public void setMmId(int mmId) {
		this.mmId = mmId;
	}

	public String getMmType() {
		return mmType;
	}

	public void setMmType(String mmType) {
		this.mmType = mmType;
	}

	public String getMmSubject() {
		return mmSubject;
	}

	public void setMmSubject(String mmSubject) {
		this.mmSubject = mmSubject;
	}

	public String getMmBody() {
		return mmBody;
	}

	public void setMmBody(String mmBody) {
		this.mmBody = mmBody;
	}

	public LocalDateTime getMmCreationDate() {
		return mmCreationDate;
	}

	public void setMmCreationDate(LocalDateTime mmCreationDate) {
		this.mmCreationDate = mmCreationDate;
	}

	public LocalDateTime getMmLastUpdate() {
		return mmLastUpdate;
	}

	public void setMmLastUpdate(LocalDateTime mmLastUpdate) {
		this.mmLastUpdate = mmLastUpdate;
	}
}
