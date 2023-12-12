package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Status {
	@NotNull
	@Max(value = 4, message = "L'id doit être inférieure ou égale à 4")
	@Min(value = 1, message = "L'id doit être supérieure ou égale à 1")
	@JsonProperty("stId")
	private int stId;

	@JsonProperty("stName")
	private String stName;

	@JsonProperty("stDescription")
	private String stDescription;

	public Status() {
	}

	public Status(int stId) {
		this.stId = stId;
	}

	public Status(int stId, String stName, String stDescription) {
		this.stId = stId;
		this.stName = stName;
		this.stDescription = stDescription;
	}

	public int getStId() {
		return stId;
	}

	public void setStId(int stId) {
		this.stId = stId;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	public String getStDescription() {
		return stDescription;
	}

	public void setStDescription(String stDescription) {
		this.stDescription = stDescription;
	}

	@Override
	public String toString() {
		return "Status [stId=" + stId + ", stName=" + stName + ", stDescription=" + stDescription + "]";
	}

}
