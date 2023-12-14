package com.example.demo.entity.builder;

import com.example.demo.entity.Status;

public class StatusBuilder {
	private int stId;
	private String stName;
	private String stDescription;

	public StatusBuilder withStId(int stId) {
		this.stId = stId;
		return this;
	}

	public StatusBuilder withStName(String stName) {
		this.stName = stName;
		return this;
	}

	public StatusBuilder withStDescription(String stDescription) {
		this.stDescription = stDescription;
		return this;
	}

	public Status build() {
		return new Status(stId, stName, stDescription);
	}

}
