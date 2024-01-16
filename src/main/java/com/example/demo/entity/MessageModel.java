package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageModel {

	@JsonProperty("mmId")
	private int mmId;

	@JsonProperty("mmLink")
	private int mmLink;

	@JsonProperty("mmSubject")
	private String mmSubject;

	@JsonProperty("mmBody")
	private String mmBody;

	@JsonProperty("mmHasEmail")
	private Boolean mmHasEmail;
	
	@JsonProperty("mmIsRelaunch")
	private Boolean mmIsRelaunch;

	@JsonProperty("mmCreationDate")
	private LocalDateTime mmCreationDate;

	@JsonProperty("mmLastUpdateDate")
	private LocalDateTime mmLastUpdateDate;

	@JsonProperty("category")
	private Category category;

	public MessageModel() {
	}

	public MessageModel(Integer mmId, Integer mmLink, String mmSubject, String mmBody, Boolean mmHasEmail, Boolean mmIsRelaunch,
			LocalDateTime mmCreationDate, LocalDateTime mmLastUpdateDate, Category category) {
		this.mmId = mmId;
		this.mmLink = mmLink;
		this.mmSubject = mmSubject;
		this.mmBody = mmBody;
		this.mmHasEmail = mmHasEmail;
		this.mmIsRelaunch = mmIsRelaunch;
		this.mmCreationDate = mmCreationDate;
		this.mmLastUpdateDate = mmLastUpdateDate;
		this.category = category;
	}

	public int getMmId() {
		return mmId;
	}

	public void setMmId(int mmId) {
		this.mmId = mmId;
	}

	public int getMmLink() {
		return mmLink;
	}

	public void setMmLink(int mmLink) {
		this.mmLink = mmLink;
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

	public Boolean getMmHasEmail() {
		return mmHasEmail;
	}

	public void setMmHasEmail(Boolean mmHasEmail) {
		this.mmHasEmail = mmHasEmail;
	}
	
	public Boolean getMmIsRelaunch() {
		return mmIsRelaunch;
	}

	public void setMmIsRelaunch(Boolean mmIsRelaunch) {
		this.mmIsRelaunch = mmIsRelaunch;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "MessageModel [mmId=" + mmId + ", mmLink=" + mmLink + ", mmSubject=" + mmSubject + ", mmBody=" + mmBody
				+ ", mmHasEmail=" + mmHasEmail + ", mmIsRelaunch=" + mmIsRelaunch + ", mmCreationDate=" + mmCreationDate
				+ ", mmLastUpdateDate=" + mmLastUpdateDate + ", category=" + category + "]";
	}

}
