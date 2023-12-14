package com.example.demo.builder;

import java.time.Instant;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.UUser;

public class RefreshTokenBuilder {
	private int rtId;
	private String rtToken;
	private Instant rtExpiryDate;
	private UUser uUser;

	public RefreshTokenBuilder withRtId(int rtId) {
		this.rtId = rtId;
		return this;
	}

	public RefreshTokenBuilder withRtToken(String rtToken) {
		this.rtToken = rtToken;
		return this;
	}

	public RefreshTokenBuilder withRtExpiryDate(Instant rtExpiryDate) {
		this.rtExpiryDate = rtExpiryDate;
		return this;
	}

	public RefreshTokenBuilder withUUser(UUser uUser) {
		this.uUser = uUser;
		return this;
	}

	public RefreshToken build() {
		return new RefreshToken(rtId, rtToken, rtExpiryDate, uUser);
	}

}
