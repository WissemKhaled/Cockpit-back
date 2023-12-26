package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class CreateGstLogDTO {

	@JsonProperty("logId")
	private int logId;

	@NotEmpty(message = "Le type du log ne doit pas être vide")
	@JsonProperty("logType")
	private String logType;

	@NotEmpty(message = "L'email ne doit pas être vide")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$", message = "Format d'email invalide")
	@JsonProperty("logEmail")
	private String logEmail;

	@NotEmpty(message = "La valeur du log ne doit pas être vide")
	@JsonProperty("logValue")
	private String logValue;

	@JsonProperty("logCreationDate")
	private LocalDateTime logCreationDate;

	public CreateGstLogDTO() {
	}

	public CreateGstLogDTO(int logId, String logType, String logEmail, String logValue, LocalDateTime logCreationDate) {
		this.logId = logId;
		this.logType = logType;
		this.logEmail = logEmail;
		this.logValue = logValue;
		this.logCreationDate = logCreationDate;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogEmail() {
		return logEmail;
	}

	public void setLogEmail(String logEmail) {
		this.logEmail = logEmail;
	}

	public String getLogValue() {
		return logValue;
	}

	public void setLogValue(String logValue) {
		this.logValue = logValue;
	}

	public LocalDateTime getLogCreationDate() {
		return logCreationDate;
	}

	public void setLogCreationDate(LocalDateTime logCreationDate) {
		this.logCreationDate = logCreationDate;
	}

}
