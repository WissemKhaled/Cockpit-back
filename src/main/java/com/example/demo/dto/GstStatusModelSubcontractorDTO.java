package com.example.demo.dto;

public class GstStatusModelSubcontractorDTO {
	private int statusMsId;
	private int statusMsFkSubcontractorId;
	private int statusMsFkMessageModelId;
	private int statusMsFkStatusId;
	
	public GstStatusModelSubcontractorDTO() {}

	public GstStatusModelSubcontractorDTO(
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
		return "GstStatusModelSubcontractorDTO [statusMsId=" + statusMsId + ", statusMsFkSubcontractorId="
				+ statusMsFkSubcontractorId + ", statusMsFkMessageModelId=" + statusMsFkMessageModelId
				+ ", statusMsFkStatusId=" + statusMsFkStatusId + "]";
	}
}