package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
	@JsonProperty("stId")
	private int stId;

	@JsonProperty("stName")
	private String stName;

	@JsonProperty("stDescription")
	private String stDescription;

	@Override
	public String toString() {
		return "Status [stId=" + stId + ", stName=" + stName + ", stDescription=" + stDescription + "]";
	}

}
