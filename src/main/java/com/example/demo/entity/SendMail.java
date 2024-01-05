package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class SendMail {

	@JsonProperty("msId")
	private int msId;

	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("msTo")
	private String msTo;

	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("msCc")
	private String msCc;

	@NotEmpty(message = "le sujet est nécessaire")
	@JsonProperty("msSubject")
	private String msSubject;

	@NotEmpty(message = "le corps du mail est nécessaire")
	@JsonProperty("msBody")
	private String msBody;

	@JsonProperty("msSendDate")
	private LocalDateTime msSendDate;

	@JsonProperty("user")
	private int user;

	public SendMail() {
	}

	public SendMail(int msId, String msTo, String msCc, String msSubject, String msBody, LocalDateTime msSendDate, int user) {
		this.msId = msId;
		this.msTo = msTo;
		this.msCc = msCc;
		this.msSubject = msSubject;
		this.msBody = msBody;
		this.msSendDate = msSendDate;
		this.user = user;
	}

	public int getMsId() {
		return msId;
	}

	public void setMsId(int msId) {
		this.msId = msId;
	}

	public String getMsTo() {
		return msTo;
	}

	public void setMsTo(String msTo) {
		this.msTo = msTo;
	}

	public String getMsCc() {
		return msCc;
	}

	public void setMsCc(String msCc) {
		this.msCc = msCc;
	}

	public String getMsSubject() {
		return msSubject;
	}

	public void setMsSubject(String msSubject) {
		this.msSubject = msSubject;
	}

	public String getMsBody() {
		return msBody;
	}

	public void setMsBody(String msBody) {
		this.msBody = msBody;
	}

	public LocalDateTime getMsSendDate() {
		return msSendDate;
	}

	public void setMsSendDate(LocalDateTime msSendDate) {
		this.msSendDate = msSendDate;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "SendMail [msId=" + msId + ", msTo=" + msTo + ", msCc=" + msCc + ", msSubject=" + msSubject + ", msBody="
				+ msBody + ", msSendDate=" + msSendDate + ", user=" + user + "]";
	}
	
	

}
