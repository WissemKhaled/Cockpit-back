package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class CreateUserDTO {
	@JsonProperty("uId")
	private int uId;

	@NotEmpty(message = "L'email ne doit pas être vide")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$", message = "Format d'email invalide")
	@JsonProperty("uEmail")
	private String uEmail;

	@NotEmpty(message = "Le mot de passe ne doit pas être vide")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?_\\-])[A-Za-z\\d@$!%*?_\\-]{6,}$", message = "Format du mot de passe invalide")
	@JsonProperty("uPassword")
	private String uPassword;

	@JsonProperty("uFirstName")
	private String uFirstName;

	@JsonProperty("uLastName")
	private String uLastName;

	@JsonProperty("uStatus")
	private boolean uStatus;

	@JsonProperty("insertionDate")
	private LocalDateTime insertionDate;

	@JsonProperty("lastUpdate")
	private LocalDateTime lastUpdate;

	public CreateUserDTO() {
	}

	public CreateUserDTO(int uId, String uEmail, String uPassword, String uFirstName, String uLastName, boolean uStatus,
			LocalDateTime insertionDate, LocalDateTime lastUpdate) {
		this.uId = uId;
		this.uEmail = uEmail;
		this.uPassword = uPassword;
		this.uFirstName = uFirstName;
		this.uLastName = uLastName;
		this.uStatus = uStatus;
		this.insertionDate = insertionDate;
		this.lastUpdate = lastUpdate;
	}

	public int getUId() {
		return uId;
	}

	public void setUId(int uId) {
		this.uId = uId;
	}

	public String getUEmail() {
		return uEmail;
	}

	public void setUEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getUPassword() {
		return uPassword;
	}

	public void setUPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public String getUFirstName() {
		return uFirstName;
	}

	public void setUFirstName(String uFirstName) {
		this.uFirstName = uFirstName;
	}

	public String getULastName() {
		return uLastName;
	}

	public void setULastName(String uLastName) {
		this.uLastName = uLastName;
	}

	public boolean isUStatus() {
		return uStatus;
	}

	public void setUStatus(boolean uStatus) {
		this.uStatus = uStatus;
	}

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}



}
