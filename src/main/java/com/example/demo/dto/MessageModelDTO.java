package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageModelDTO {
	@JsonProperty("mmId")
	private int mmId;

	@JsonProperty("mmType")
	private String mmType;

	@JsonProperty("mmSubject")
	private String mmSubject;

	@JsonProperty("mmBody")
	private String mmBody;

	@JsonProperty("mmCreationDate")
	private LocalDateTime mmCreationDate;

	@JsonProperty("mmLastUpdate")
	private LocalDateTime mmLastUpdate;

	public MessageModelDTO() {
	}

	public MessageModelDTO(int mmId, String mmType, String mmSubject, String mmBody, LocalDateTime mmCreationDate,
			LocalDateTime mmLastUpdate) {
		this.mmId = mmId;
		this.mmType = mmType;
		this.mmSubject = mmSubject;
		this.mmBody = mmBody;
		this.mmCreationDate = mmCreationDate;
		this.mmLastUpdate = mmLastUpdate;
	}

	public int getMmId() {
		return mmId;
	}

	public void setMmId(int mmId) {
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

	public LocalDateTime getMmLastUpdate() {
		return mmLastUpdate;
	}

	public void setMmLastUpdate(LocalDateTime mmLastUpdate) {
		this.mmLastUpdate = mmLastUpdate;
	}

}
