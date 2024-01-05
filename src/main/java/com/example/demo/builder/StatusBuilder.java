package com.example.demo.builder;

import com.example.demo.entity.Status;

public class StatusBuilder {
	private int stId;
	private String stName;

	public StatusBuilder withStId(int stId) {
		this.stId = stId;
		return this;
	}

	public StatusBuilder withStName(String stName) {
		this.stName = stName;
		return this;
	}

	public Status build() {
		return new Status(stId, stName);
	}

}
