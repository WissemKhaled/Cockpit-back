package com.example.demo.dto;

public class GstLogResponseDTO {
	private String status;
	private String message;

	public GstLogResponseDTO() {
	}

	public GstLogResponseDTO(String status, String message) {
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

}
