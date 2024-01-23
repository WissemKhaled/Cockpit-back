package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelTracking {
	@JsonProperty("mtId")
	private int mtId;
	
	@JsonProperty("mtFkContractId")
	private int mtFkContractId;
	
	@JsonProperty("mtFkMessageModelId")
	private int mtFkMessageModelId;
	
	@JsonProperty("mtFkStatusId")
	private int mtFkStatusId;
	
	@JsonProperty("mtFkCategoryId")
	private int mtFkCategoryId;
	
	@JsonProperty("mtSendDate")
	private LocalDateTime mtSendDate;
	
	@JsonProperty("mtValidationDate")
	private LocalDateTime mtValidationDate;
	
	@JsonProperty("messageModel")
	private MessageModel messageModel;
	
	@JsonProperty("contract")
	private Contract contract;

	public ModelTracking() {}

	public ModelTracking(int mtId, int mtFkContractId, int mtFkMessageModelId, int mtFkStatusId, int mtFkCategoryId,
			LocalDateTime mtSendDate, LocalDateTime mtValidationDate, MessageModel messageModel, Contract contract) {
		this.mtId = mtId;
		this.mtFkContractId = mtFkContractId;
		this.mtFkMessageModelId = mtFkMessageModelId;
		this.mtFkStatusId = mtFkStatusId;
		this.mtFkCategoryId = mtFkCategoryId;
		this.mtSendDate = mtSendDate;
		this.mtValidationDate = mtValidationDate;
		this.messageModel = messageModel;
		this.contract = contract;
	}
	
	public ModelTracking(int mtId, int mtFkContractId, int mtFkMessageModelId, int mtFkStatusId, int mtFkCategoryId,
			LocalDateTime mtSendDate, LocalDateTime mtValidationDate) {
		this.mtId = mtId;
		this.mtFkContractId = mtFkContractId;
		this.mtFkMessageModelId = mtFkMessageModelId;
		this.mtFkStatusId = mtFkStatusId;
		this.mtFkCategoryId = mtFkCategoryId;
		this.mtSendDate = mtSendDate;
		this.mtValidationDate = mtValidationDate;
	}

	public int getMtId() {
		return mtId;
	}

	public void setMtId(int mtId) {
		this.mtId = mtId;
	}

	public int getMtFkContractId() {
		return mtFkContractId;
	}

	public void setMtFkContractId(int mtFkContractId) {
		this.mtFkContractId = mtFkContractId;
	}

	public int getMtFkMessageModelId() {
		return mtFkMessageModelId;
	}

	public void setMtFkMessageModelId(int mtFkMessageModelId) {
		this.mtFkMessageModelId = mtFkMessageModelId;
	}

	public int getMtFkStatusId() {
		return mtFkStatusId;
	}

	public void setMtFkStatusId(int mtFkStatusId) {
		this.mtFkStatusId = mtFkStatusId;
	}

	public int getMtFkCategoryId() {
		return mtFkCategoryId;
	}

	public void setMtFkCategoryId(int mtFkCategoryId) {
		this.mtFkCategoryId = mtFkCategoryId;
	}

	public LocalDateTime getMtSendDate() {
		return mtSendDate;
	}

	public void setMtSendDate(LocalDateTime mtSendDate) {
		this.mtSendDate = mtSendDate;
	}

	public LocalDateTime getMtValidationDate() {
		return mtValidationDate;
	}

	public void setMtValidationDate(LocalDateTime mtValidationDate) {
		this.mtValidationDate = mtValidationDate;
	}
	
	public MessageModel getMessageModel() {
		return messageModel;
	}

	public void setMessageModel(MessageModel messageModel) {
		this.messageModel = messageModel;
	}
	
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@Override
	public String toString() {
		return "ModelTracking [mtId=" + mtId + ", mtFkContractId=" + mtFkContractId + ", mtFkMessageModelId="
				+ mtFkMessageModelId + ", mtFkStatusId=" + mtFkStatusId + ", mtFkCategoryId=" + mtFkCategoryId
				+ ", mtSendDate=" + mtSendDate + ", mtValidationDate=" + mtValidationDate + ", messageModel=" 
				+ messageModel + ", contract=" + contract +"]";
	}
}
