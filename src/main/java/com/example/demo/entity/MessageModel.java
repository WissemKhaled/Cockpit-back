package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageModel {

	@JsonProperty("mmId")
	private Integer mmId;

	@JsonProperty("mmCategory")
	private String mmCategory;

	@JsonProperty("mmType")
	private String mmType;

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

	public MessageModel() {
	}

	public MessageModel(Integer mmId, String mmType, String mmSubject, String mmBody, LocalDateTime mmCreationDate,
			LocalDateTime mmLastUpdateDate, Status status, String mmCategory) {
		this.mmId = mmId;
		this.mmType = mmType;
		this.mmSubject = mmSubject;
		this.mmBody = mmBody;
		this.mmCreationDate = mmCreationDate;
		this.mmLastUpdateDate = mmLastUpdateDate;
		this.mmCategory = mmCategory;
		this.status = status;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MessageModel [mmId=" + mmId + ", mmType=" + mmType + ", mmSubject=" + mmSubject + ", mmBody=" + mmBody
				+ ", mmCreationDate=" + mmCreationDate + ", mmLastUpdateDate=" + mmLastUpdateDate + ", status=" + status
				+ "]";
	}

}
