package com.example.demo.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshToken {

	@JsonProperty("rtId")
	private int rtId;

	@JsonProperty("rtToken")
	private String rtToken;

	@JsonProperty("rtExpiryDate")
	private Instant rtExpiryDate;

	@JsonProperty("uUser")
	private UUser uUser;

	public RefreshToken() {
	}

	public RefreshToken(int rtId, String rtToken, Instant rtExpiryDate, UUser uUser) {
		this.rtId = rtId;
		this.rtToken = rtToken;
		this.rtExpiryDate = rtExpiryDate;
		this.uUser = uUser;
	}

	public int getRtId() {
		return rtId;
	}

	public void setRtId(int rtId) {
		this.rtId = rtId;
	}

	public String getRtToken() {
		return rtToken;
	}

	public void setRtToken(String rtToken) {
		this.rtToken = rtToken;
	}

	public Instant getRtExpiryDate() {
		return rtExpiryDate;
	}

	public void setRtExpiryDate(Instant rtExpiryDate) {
		this.rtExpiryDate = rtExpiryDate;
	}

	public UUser getUUser() {
		return uUser;
	}

	public void setUUser(UUser uUser) {
		this.uUser = uUser;
	}

	@Override
	public String toString() {
		return "RefreshToken [rtId=" + rtId + ", rtToken=" + rtToken + ", rtExpiryDate=" + rtExpiryDate + ", uUser="
				+ uUser + "]";
	}

}
