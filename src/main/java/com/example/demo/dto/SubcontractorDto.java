package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SubcontractorDto {
	@JsonProperty("sId")
	private int sId;

	@NotEmpty(message = "le nom est nécessaire")
	@Size(min = 2, max = 250, message = "la longueur du nom doit etre entre 2 et 25O caractères")
	@JsonProperty("sName")
	private String sName;

	@NotEmpty(message = "l'email est nécessaire")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("sEmail")
	private String sEmail;

	@JsonProperty("sCreationDate")
	private LocalDateTime sCreationDate;

	@JsonProperty("sLastUpdateDate")
	private LocalDateTime sLastUpdateDate;

	@Valid
	@JsonProperty("status")
	private Status status;

	@JsonProperty("serviceProvidersIds")
	private List<Integer> serviceProvidersIds;

	public SubcontractorDto() {
	}

	public SubcontractorDto(int sId, String sName, String sEmail, LocalDateTime sCreationDate,
			LocalDateTime sLastUpdateDate, Status status, List<Integer> serviceProvidersIds) {
		this.sId = sId;
		this.sName = sName;
		this.sEmail = sEmail;
		this.sCreationDate = sCreationDate;
		this.sLastUpdateDate = sLastUpdateDate;
		this.status = status;
		this.serviceProvidersIds = serviceProvidersIds;
	}

	public int getSId() {
		return sId;
	}

	public void setSId(int sId) {
		this.sId = sId;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String sName) {
		this.sName = sName;
	}

	public String getSEmail() {
		return sEmail;
	}

	public void setSEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public LocalDateTime getSCreationDate() {
		return sCreationDate;
	}

	public void setSCreationDate(LocalDateTime sCreationDate) {
		this.sCreationDate = sCreationDate;
	}

	public LocalDateTime getSLastUpdateDate() {
		return sLastUpdateDate;
	}

	public void setSLastUpdateDate(LocalDateTime sLastUpdateDate) {
		this.sLastUpdateDate = sLastUpdateDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Integer> getServiceProvidersIds() {
		return serviceProvidersIds;
	}

	public void setServiceProvidersIds(List<Integer> serviceProvidersIds) {
		this.serviceProvidersIds = serviceProvidersIds;
	}

	@Override
	public String toString() {
		return "SubcontractorDto [sId=" + sId + ", sName=" + sName + ", sEmail=" + sEmail + ", sCreationDate="
				+ sCreationDate + ", sLastUpdateDate=" + sLastUpdateDate + ", status=" + status
				+ ", serviceProvidersIds=" + serviceProvidersIds + "]";
	}

}
