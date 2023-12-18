package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GstLog {
	@JsonProperty("logId")
	private int logId;

	@JsonProperty("logType")
	private String logType;

	@JsonProperty("logEmail")
	private String logEmail;
	
	@JsonProperty("logPassword")
	private String logPassword;
	
	@JsonProperty("logValue")
	private String logValue;

	@JsonProperty("logCreationDate")
	private LocalDateTime logCreationDate;
	
	@JsonProperty("logLastUpdate")
	private LocalDateTime logLastUpdate;

	public GstLog() {
	}

	public GstLog(int logId, String logType, String logEmail, String password, String logValue, LocalDateTime logCreationDate, LocalDateTime logLastUpdate) {
		this.logId = logId;
		this.logType = logType;
		this.logEmail = logEmail;
		this.logValue = logValue;
		this.logCreationDate = logCreationDate;
		this.logLastUpdate = logLastUpdate;
	}

	public GstLog(int logId, String logType, String logEmail, String logValue, LocalDateTime logCreationDate) {
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

	public String getLogPassword() {
		return logPassword;
	}

	public void setLogPassword(String logPassword) {
		this.logPassword = logPassword;
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

	public LocalDateTime getLogLastUpdate() {
		return logLastUpdate;
	}

	public void setLogLastUpdate(LocalDateTime logLastUpdate) {
		this.logLastUpdate = logLastUpdate;
	}

	@Override
	public String toString() {
		return "GstLog [logId=" + logId + ", logType=" + logType + ", logEmail=" + logEmail + ", logPassword="
				+ logPassword + ", logValue=" + logValue + ", logCreationDate=" + logCreationDate + ", logLastUpdate="
				+ logLastUpdate + "]";
	}
}
