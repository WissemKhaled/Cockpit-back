package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDTO {

	@JsonProperty("catId")
	private int catId;

	@JsonProperty("catName")
	private String catName;

	public CategoryDTO() {

	}

	public CategoryDTO(int catId, String catName) {
		this.catId = catId;
		this.catName = catName;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

}
