package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class SendMailDTO {

	@JsonProperty("msId")
	private int msId;

	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("msTo")
	private String msTo;

	@JsonProperty("msCc")
	private String msCc;

	@NotEmpty(message = "le sujet est nécessaire")
	@JsonProperty("msSubject")
	private String msSubject;

	@JsonProperty("msBody")
	private String msBody;

	@JsonProperty("msSendDate")
	private LocalDateTime msSendDate;

	@JsonProperty("msFkUserId")
	private int msFkUserId;

	public SendMailDTO() {
	}

	public SendMailDTO(int msId,
			@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide") String msTo,
			@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide") String msCc,
			@NotEmpty(message = "le sujet est nécessaire") String msSubject,
			@NotEmpty(message = "le corps du mail est nécessaire") String msBody, LocalDateTime msSendDate,
			int msFkUserId) {
		this.msId = msId;
		this.msTo = msTo;
		this.msCc = msCc;
		this.msSubject = msSubject;
		this.msBody = msBody;
		this.msSendDate = msSendDate;
		this.msFkUserId = msFkUserId;
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

	public int getMsFkUserId() {
		return msFkUserId;
	}

	public void setMsFkUserId(int msFkUserId) {
		this.msFkUserId = msFkUserId;
	}

	@Override
	public String toString() {
		return "SendMailDTO [msId=" + msId + ", msTo=" + msTo + ", msCc=" + msCc + ", msSubject=" + msSubject
				+ ", msBody=" + msBody + ", msSendDate=" + msSendDate + ", msFkUserId=" + msFkUserId + "]";
	}

}
