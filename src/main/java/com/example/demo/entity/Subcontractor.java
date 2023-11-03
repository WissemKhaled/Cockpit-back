package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Subcontractor {

	@JsonProperty("sId")
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

}
