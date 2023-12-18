package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	public GstLogDTO(int logId, String logType, String logEmail, String logValue, LocalDateTime logCreationDate) {
		this.logId = logId;
		this.logType = logType;
		this.logEmail = logEmail;
		this.logValue = logValue;
		this.logCreationDate = logCreationDate;
	}

	public GstLogDTO() {
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
