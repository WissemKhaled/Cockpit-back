package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StatusDto {

	@NotNull
	@Max(value = 4, message = "L'id doit être inférieure ou égale à 4")
	@Min(value = 1, message = "L'id doit être supérieure ou égale à 1")
	@JsonProperty("stId")
	private int stId;

	@JsonProperty("stName")
	private String stName;


	public StatusDto() {
	}

	public StatusDto(int stId, String stName) {
		this.stId = stId;
		this.stName = stName;
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

	@Override
	public String toString() {
		return "StatusDto [stId=" + stId + ", stName=" + stName + "]";
	}

}
