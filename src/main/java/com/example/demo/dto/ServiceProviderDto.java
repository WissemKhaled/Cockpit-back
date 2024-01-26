package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ServiceProviderDto {
	@JsonProperty("spId")
	private int spId;

	@NotEmpty(message = "le prénom ne doit pas étre null")
	@Size(min = 2, max = 250, message = "la longueur du prénom doit etre entre 2 et 25O caractères")
	@Pattern(regexp = "^[a-zA-Z]{2,}$", message = "Le format du prénom saisi est invalide")
	@JsonProperty("spFirstName")
	private String spFirstName;

	@NotEmpty(message = "le nom ne doit pas étre null")
	@Size(min = 2, max = 250, message = "la longueur du nom doit etre entre 2 et 25O caractères")
	@Pattern(regexp = "^[a-zA-Z]{2,}$", message = "Le format du nom saisi est invalide")
	@JsonProperty("spName")
	private String spName;

	@NotEmpty(message = "l'email ne doit pas étre null")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("spEmail")
	private String spEmail;

	@JsonProperty("spCreationDate")
	private LocalDateTime spCreationDate;

	@JsonProperty("spLastUpdateDate")
	private LocalDateTime spLastUpdateDate;

	@JsonProperty("spStatus")
	private Status spStatus;

	@JsonProperty("subcontractorSName")
	private String subcontractorSName;

	@JsonProperty("subcontractorSEmail")
	private String subcontractorSEmail;

	@JsonProperty("newPage")
	private int newPage;

	@JsonProperty("alertsList")
	private List<Integer> alertsList;

	@JsonProperty("isForeign")
	private Boolean isForeign;

	public ServiceProviderDto() {
	}

	public ServiceProviderDto(int spId, String spFirstName, String spName, String spEmail, LocalDateTime spCreationDate,
			LocalDateTime spLastUpdateDate, Status spStatus, String subcontractorSName, String subcontractorSEmail) {
		this.spId = spId;
		this.spFirstName = spFirstName;
		this.spName = spName;
		this.spEmail = spEmail;
		this.spCreationDate = spCreationDate;
		this.spLastUpdateDate = spLastUpdateDate;
		this.spStatus = spStatus;
		this.subcontractorSName = subcontractorSName;
		this.subcontractorSEmail = subcontractorSEmail;
	}

	public int getSpId() {
		return spId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public String getSpFirstName() {
		return spFirstName;
	}

	public void setSpFirstName(String spFirstName) {
		this.spFirstName = spFirstName;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getSpEmail() {
		return spEmail;
	}

	public void setSpEmail(String spEmail) {
		this.spEmail = spEmail;
	}

	public LocalDateTime getSpCreationDate() {
		return spCreationDate;
	}

	public void setSpCreationDate(LocalDateTime spCreationDate) {
		this.spCreationDate = spCreationDate;
	}

	public LocalDateTime getSpLastUpdateDate() {
		return spLastUpdateDate;
	}

	public void setSpLastUpdateDate(LocalDateTime spLastUpdateDate) {
		this.spLastUpdateDate = spLastUpdateDate;
	}

	public Status getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(Status spStatus) {
		this.spStatus = spStatus;
	}

	public String getSubcontractorSName() {
		return subcontractorSName;
	}

	public void setSubcontractorSName(String subcontractorSName) {
		this.subcontractorSName = subcontractorSName;
	}

	public int getNewPage() {
		return newPage;
	}

	public void setNewPage(int newPage) {
		this.newPage = newPage;
	}

	public List<Integer> getAlertsList() {
		return alertsList;
	}

	public void setAlertsList(List<Integer> alertsList) {
		this.alertsList = alertsList;
	}

	public String getSubcontractorSEmail() {
		return subcontractorSEmail;
	}

	public void setSubcontractorSEmail(String subcontractorSEmail) {
		this.subcontractorSEmail = subcontractorSEmail;
	}

	public Boolean getIsForeign() {
		return isForeign;
	}

	public void setIsForeign(Boolean isForeign) {
		this.isForeign = isForeign;
	}

	@Override
	public String toString() {
		return "ServiceProviderDto [spId=" + spId + ", spFirstName=" + spFirstName + ", spName=" + spName + ", spEmail="
				+ spEmail + ", spCreationDate=" + spCreationDate + ", spLastUpdateDate=" + spLastUpdateDate
				+ ", spStatus=" + spStatus + ", subcontractorSName=" + subcontractorSName + ", subcontractorSEmail="
				+ subcontractorSEmail + ", newPage=" + newPage + ", alertsList=" + alertsList + ", isForeign="
				+ isForeign + "]";
	}

	

}
