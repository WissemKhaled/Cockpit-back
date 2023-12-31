package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GstStatusModelSubcontractor {
	@JsonProperty("statusMsId")
	private int statusMsId;
	
	@JsonProperty("statusMsFkSubcontractorId")
	private int statusMsFkSubcontractorId;
	
	@JsonProperty("statusMsFkMessageModelId")
	private int statusMsFkMessageModelId;
	
	@JsonProperty("statusMsFkStatusId")
	private int statusMsFkStatusId;
	
	@JsonProperty("statusMsSentDate")
	private LocalDateTime statusMsSentDate;
	
	@JsonProperty("statusMsValidationDate")
	private LocalDateTime statusMsValidationDate;
	
	public GstStatusModelSubcontractor() {}

	public GstStatusModelSubcontractor(int statusMsId, int statusMsFkSubcontractorId, int statusMsFkMessageModelId,
			int statusMsFkStatusId, LocalDateTime statusMsSentDate, LocalDateTime statusMsValidationDate) {
		this.statusMsId = statusMsId;
		this.statusMsFkSubcontractorId = statusMsFkSubcontractorId;
		this.statusMsFkMessageModelId = statusMsFkMessageModelId;
		this.statusMsFkStatusId = statusMsFkStatusId;
		this.statusMsSentDate = statusMsSentDate;
		this.statusMsValidationDate = statusMsValidationDate;
	}

	public int getStatusMsId() {
		return statusMsId;
	}

	public void setStatusMsId(int statusMsId) {
		this.statusMsId = statusMsId;
	}

	public int getStatusMsFkSubcontractorId() {
		return statusMsFkSubcontractorId;
	}

	public void setStatusMsFkSubcontractorId(int statusMsFkSubcontractorId) {
		this.statusMsFkSubcontractorId = statusMsFkSubcontractorId;
	}

	public int getStatusMsFkMessageModelId() {
		return statusMsFkMessageModelId;
	}

	public void setStatusMsFkMessageModelId(int statusMsFkMessageModelId) {
		this.statusMsFkMessageModelId = statusMsFkMessageModelId;
	}

	public int getStatusMsFkStatusId() {
		return statusMsFkStatusId;
	}

	public void setStatusMsFkStatusId(int statusMsFkStatusId) {
		this.statusMsFkStatusId = statusMsFkStatusId;
	}

	public LocalDateTime getStatusMsSentDate() {
		return statusMsSentDate;
	}

	public void setStatusMsSentDate(LocalDateTime statusMsSentDate) {
		this.statusMsSentDate = statusMsSentDate;
	}

	public LocalDateTime getStatusMsValidationDate() {
		return statusMsValidationDate;
	}

	public void setStatusMsValidationDate(LocalDateTime statusMsValidationDate) {
		this.statusMsValidationDate = statusMsValidationDate;
	}

	@Override
	public String toString() {
		return "GstStatusModelSubcontractor [statusMsId=" + statusMsId + ", statusMsFkSubcontractorId="
				+ statusMsFkSubcontractorId + ", statusMsFkMessageModelId=" + statusMsFkMessageModelId
				+ ", statusMsFkStatusId=" + statusMsFkStatusId + ", statusMsSentDate=" + statusMsSentDate
				+ ", statusMsValidationDate=" + statusMsValidationDate + "]";
	}
}
