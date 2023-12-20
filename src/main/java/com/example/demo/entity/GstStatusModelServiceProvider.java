package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GstStatusModelServiceProvider {
	@JsonProperty("statusMspId")
	private int statusMspId;
	
	@JsonProperty("statusMspFkServiceProviderId")
	private int statusMspFkServiceProviderId;
	
	@JsonProperty("statusMspFkMessageModelId")
	private int statusMspFkMessageModelId;
	
	@JsonProperty("statusMspFkStatusId")
	private int statusMspFkStatusId;
	
	public GstStatusModelServiceProvider() {}
	
	public GstStatusModelServiceProvider(
		int statusMspId,
		int statusMspFkServiceProviderId,
		int statusMspFkMessageModelId,
		int statusMspFkStatusId
	) {
		this.statusMspId = statusMspId;
		this.statusMspFkServiceProviderId = statusMspFkServiceProviderId;
		this.statusMspFkMessageModelId = statusMspFkMessageModelId;
		this.statusMspFkStatusId = statusMspFkStatusId;
	}

	public int getStatusMspId() {
		return statusMspId;
	}

	public void setStatusMspId(int statusMspId) {
		this.statusMspId = statusMspId;
	}

	public int getStatusMspFkServiceProviderId() {
		return statusMspFkServiceProviderId;
	}

	public void setStatusMspFkServiceProviderId(int statusMspFkServiceProviderId) {
		this.statusMspFkServiceProviderId = statusMspFkServiceProviderId;
	}

	public int getStatusMspFkMessageModelId() {
		return statusMspFkMessageModelId;
	}

	public void setStatusMspFkMessageModelId(int statusMspFkMessageModelId) {
		this.statusMspFkMessageModelId = statusMspFkMessageModelId;
	}

	public int getStatusMspFkStatusId() {
		return statusMspFkStatusId;
	}

	public void setStatusMspFkStatusId(int statusMspFkStatusId) {
		this.statusMspFkStatusId = statusMspFkStatusId;
	}

	@Override
	public String toString() {
		return "GstStatusModelServiceProvider [statusMspId=" + statusMspId + ", statusMspFkServiceProviderId="
				+ statusMspFkServiceProviderId + ", statusMspFkMessageModelId=" + statusMspFkMessageModelId
				+ ", statusMspFkStatusId=" + statusMspFkStatusId + "]";
	}
}
