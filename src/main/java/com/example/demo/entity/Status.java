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
	private String stname;

	@JsonProperty("stDescription")
	private String stDescription;

	public Status() {
	}

	public Status(int stId) {
		this.stId = stId;
	}

	public Status(int stId, String stname, String stDescription) {
		this.stId = stId;
		this.stname = stname;
		this.stDescription = stDescription;
	}

	public int getStId() {
		return stId;
	}

	public void setStId(int stId) {
		this.stId = stId;
	}

	public String getStname() {
		return stname;
	}

	public void setStname(String stname) {
		this.stname = stname;
	}

	public String getStDescription() {
		return stDescription;
	}

	public void setStDescription(String stDescription) {
		this.stDescription = stDescription;
	}

	@Override
	public String toString() {
		return "Status [stId=" + stId + ", stName=" + stname + ", stDescription=" + stDescription + "]";
	}

}
