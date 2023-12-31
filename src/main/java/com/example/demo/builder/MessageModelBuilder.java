package com.example.demo.builder;

import java.time.LocalDateTime;

import com.example.demo.entity.MessageModel;
import com.example.demo.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageModelBuilder {
	private Integer mmId;
	private String mmType;
	private String mmCategory;
	private String mmSubject;
	private String mmBody;
	private LocalDateTime mmCreationDate;
	private LocalDateTime mmLastUpdateDate;
	private Status status;
	private Integer statusMspId;
	private Integer statusMspFkServiceProviderId;
	private Integer statusMspFkStatusId;
//	private Status stName;
//	private Integer spId;
//	private Integer stId;

	public MessageModelBuilder withMmId(Integer mmId) {
		this.mmId = mmId;
		return this;
	}

	public MessageModelBuilder withMmType(String mmType) {
		this.mmType = mmType;
		return this;
	}

	public MessageModelBuilder withMmCategory(String mmCategory) {
		this.mmCategory = mmCategory;
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

	public MessageModelBuilder withStatusMspId(Integer statusMspId) {
		this.statusMspId = statusMspId;
		return this;
	}
	
	public MessageModelBuilder withStatusMspFkServiceProviderId(Integer statusMspFkServiceProviderId) {
		this.statusMspFkServiceProviderId = statusMspFkServiceProviderId;
		return this;
	}
	
	public MessageModelBuilder withStatusMspFkStatusId(Integer statusMspFkStatusId) {
		this.statusMspFkStatusId = statusMspFkStatusId;
		return this;
	}
	
//	public MessageModelBuilder withStName(Status stName) {
//		this.stName = stName;
//		return this;
//	}
//	
//	public MessageModelBuilder withSpId(Integer spId) {
//		this.spId = spId;
//		return this;
//	}
//	
//	public MessageModelBuilder withStId(Integer stId) {
//		this.stId = stId;
//		return this;
//	}

//	public MessageModel build() {
//		return new MessageModel(mmId, mmCategory, mmType, mmSubject, mmBody, mmCreationDate, mmLastUpdateDate, status, statusMspId, 
//				statusMspFkServiceProviderId, statusMspFkStatusId, stName, spId, stId);
//	}
	
	public MessageModel build() {
		return new MessageModel(mmId, mmCategory, mmType, mmSubject, mmBody, mmCreationDate, mmLastUpdateDate, status, statusMspId, 
				statusMspFkServiceProviderId, statusMspFkStatusId);
	}

}
