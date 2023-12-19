package com.example.demo.builder;

import java.time.LocalDateTime;

import com.example.demo.entity.GstLog;

public class GstLogBuilder {
	private int logId;
	private String logType;
	private String logEmail;
	private String logValue;
	private LocalDateTime logCreationDate;

	public GstLogBuilder setLogId(int logId) {
		this.logId = logId;
		return this;
	}

	public GstLogBuilder setLogType(String logType) {
		this.logType = logType;
		return this;
	}

	public GstLogBuilder setLogEmail(String logEmail) {
		this.logEmail = logEmail;
		return this;
	}

	public GstLogBuilder setLogValue(String logValue) {
		this.logValue = logValue;
		return this;
	}

	public GstLogBuilder setLogCreationDate(LocalDateTime logCreationDate) {
		this.logCreationDate = logCreationDate;
		return this;
	}

	public GstLog build() {
		return new GstLog(logId, logType, logEmail, logValue, logCreationDate);
	}

}
