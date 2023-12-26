package com.example.demo.builder;

import java.time.LocalDateTime;

import com.example.demo.entity.MessageModel;
import com.example.demo.entity.Status;

public class MessageModelBuilder {
	private Integer mmId;
	private String mmCategory;
	private String mmType;
	private String mmSubject;
	private String mmBody;
	private LocalDateTime mmCreationDate;
	private LocalDateTime mmLastUpdateDate;
	private Status status;

	public MessageModelBuilder withMmId(Integer mmId) {
		this.mmId = mmId;
		return this;
	}

	public MessageModelBuilder withMmCategory(String mmCategory) {
		this.mmType = mmType;
		return this;
	}

	public MessageModelBuilder withMmType(String mmType) {
		this.mmType = mmType;
		return this;
	}

	public MessageModelBuilder withMmSubject(String mmSubject) {
		this.mmSubject = mmSubject;
		return this;
	}

	public MessageModelBuilder withMmBody(String mmBody) {
		this.mmBody = mmBody;
		return this;
	}

	public MessageModelBuilder withMmCreationDate(LocalDateTime mmCreationDate) {
		this.mmCreationDate = mmCreationDate;
		return this;
	}

	public MessageModelBuilder withMmLastUpdateDate(LocalDateTime mmLastUpdateDate) {
		this.mmLastUpdateDate = mmLastUpdateDate;
		return this;
	}

	public MessageModelBuilder withStatus(Status status) {
		this.status = status;
		return this;
	}

	public MessageModel build() {
		return new MessageModel(mmId, mmType, mmSubject, mmBody, mmCreationDate, mmLastUpdateDate, status, mmCategory);
	}

}
