package com.example.demo.entity;

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
	
	public GstStatusModelSubcontractor() {}

	public GstStatusModelSubcontractor(
		int statusMsId, 
		int statusMsFkSubcontractorId, 
		int statusMsFkMessageModelId,
		int statusMsFkStatusId
	) {
		this.statusMsId = statusMsId;
		this.statusMsFkSubcontractorId = statusMsFkSubcontractorId;
		this.statusMsFkMessageModelId = statusMsFkMessageModelId;
		this.statusMsFkStatusId = statusMsFkStatusId;
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
	
	@Override
	public String toString() {
		return "GstStatusModelSubcontractor [statusMsId=" + statusMsId + ", statusMsFkSubcontractorId="
				+ statusMsFkSubcontractorId + ", statusMsFkMessageModelId=" + statusMsFkMessageModelId
				+ ", statusMsFkStatusId=" + statusMsFkStatusId + "]";
	}
}
