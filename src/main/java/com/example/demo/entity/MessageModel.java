package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageModel {

	@JsonProperty("mmId")
	private Integer mmId;

	@JsonProperty("mmType")
	private String mmType;

	@JsonProperty("mmCategory")
	private String mmCategory;

	@JsonProperty("mmSubject")
	private String mmSubject;

	@JsonProperty("mmBody")
	private String mmBody;

	@JsonProperty("mmCreationDate")
	private LocalDateTime mmCreationDate;

	@JsonProperty("mmLastUpdateDate")
	private LocalDateTime mmLastUpdateDate;

	@JsonProperty("status")
	private Status status;
	
	@JsonProperty("statusMspId")
	private Integer statusMspId;
	
	@JsonProperty("statusMspFkServiceProviderId")
	private Integer statusMspFkServiceProviderId;
	
	@JsonProperty("statusMspFkStatusId")
	private Integer statusMspFkStatusId;

	@JsonProperty("mmStatusId")
	private Status mmStatusId;

	public MessageModel() {
	}

	public MessageModel(Integer mmId, String mmType, String mmCategory, String mmSubject, String mmBody,
			LocalDateTime mmCreationDate, LocalDateTime mmLastUpdateDate, Status status, Integer statusMspId,
			Integer statusMspFkServiceProviderId, Integer statusMspFkStatusId) {
		this.mmId = mmId;
		this.mmType = mmType;
		this.mmCategory = mmCategory;
		this.mmSubject = mmSubject;
		this.mmBody = mmBody;
		this.mmCreationDate = mmCreationDate;
		this.mmLastUpdateDate = mmLastUpdateDate;
		this.status = status;
		this.statusMspId = statusMspId;
		this.statusMspFkServiceProviderId = statusMspFkServiceProviderId;
		this.statusMspFkStatusId = statusMspFkStatusId;
		this.mmStatusId = status;

	}



	public String getMmCategory() {
		return mmCategory;
	}

	public void setMmCategory(String mmCategory) {
		this.mmCategory = mmCategory;
	}

	public Integer getMmId() {
		return mmId;
	}

	public void setMmId(Integer mmId) {
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

	public LocalDateTime getMmLastUpdateDate() {
		return mmLastUpdateDate;
	}

	public void setMmLastUpdateDate(LocalDateTime mmLastUpdateDate) {
		this.mmLastUpdateDate = mmLastUpdateDate;
	}

	public Status getMmStatusId() {
		return mmStatusId;
	}

	public void setMmStatusId(Status mmStatusId) {
		this.mmStatusId = mmStatusId;
	}
	
	public Integer getStatusMspId() {
		return statusMspId;
	}

	public void setStatusMspId(Integer statusMspId) {
		this.statusMspId = statusMspId;
	}
	
	public Integer getStatusMspFkServiceProviderId() {
		return statusMspFkServiceProviderId;
	}

	public void setStatusMspFkServiceProviderId(Integer statusMspFkServiceProviderId) {
		this.statusMspFkServiceProviderId = statusMspFkServiceProviderId;
	}
	
	public Integer getStatusMspFkStatusId() {
		return statusMspFkStatusId;
	}

	public void setStatusMspFkStatusId(Integer statusMspFkStatusId) {
		this.statusMspFkStatusId = statusMspFkStatusId;
	}

	@Override
	public String toString() {
		return "MessageModel [mmId=" + mmId + ", mmCategory= " + mmCategory + ", mmType=" + mmType + ", mmSubject=" + mmSubject + ", mmBody=" + mmBody
				+ ", mmCreationDate=" + mmCreationDate + ", mmLastUpdateDate=" + mmLastUpdateDate + ", status=" + status 
				+ ", statusMspId=" + statusMspId + ", statusMspFkServiceProviderId=" + statusMspFkServiceProviderId
				+ ", statusMspFkStatusId=" + statusMspFkStatusId + ", statusMspFkStatusId=" + statusMspFkStatusId 
				+ ", mmCreationDate=" + mmCreationDate + ", mmLastUpdateDate=" + mmLastUpdateDate + ", status=" + mmStatusId
				+ "]";
	}

}
