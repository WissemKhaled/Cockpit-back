package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subcontractor {
	private int sId;

	@JsonProperty("sName")
	private String sName;

	@JsonProperty("sEmail")
	private String sEmail;

	@JsonProperty("status")
	private Status status;

	public Subcontractor(String sName, String sEmail) {
		this.sName = sName;
		this.sEmail = sEmail;
	}

	@Override
	public String toString() {
		return "Subcontractor [sId=" + sId + ", sName=" + sName + ", sEmail=" + sEmail + ", status=" + status + "]";
	}

}
