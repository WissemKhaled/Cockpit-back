package com.example.demo.dto;

public class ResetPwdExpirationResponseDTO {
	private String status;
	private String message;
	private boolean isValid;

	public ResetPwdExpirationResponseDTO() {
	}

	public ResetPwdExpirationResponseDTO(String status, String message, boolean isValid) {
		this.status = status;
		this.message = message;
		this.isValid = isValid;
	}

	public ResetPwdExpirationResponseDTO(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
