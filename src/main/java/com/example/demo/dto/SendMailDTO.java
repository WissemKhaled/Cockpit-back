package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class SendMailDTO {

	@JsonProperty("msId")
	private int msId;

	@JsonProperty("msSender")
	private String msSender;

	@NotNull
	@NotEmpty(message = "l'email est nécessaire")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("msTo")
	private String msTo;

	@NotEmpty(message = "l'email est nécessaire")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("msCc")
	private String msCc;

	@NotEmpty(message = "le sujet est nécessaire")
	@JsonProperty("msSubject")
	private String msSubject;

	@NotEmpty(message = "le corps du mail est nécessaire")
	@JsonProperty("msBody")
	private String msBody;

	@JsonProperty("msError")
	private String msError;

	@Max(value = 3, message = "L'id doit être inférieure ou égale à 3")
	@Min(value = 1, message = "L'id doit être supérieure ou égale à 1")
	@JsonProperty("msStatus")
	private int msStatus;

	@JsonProperty("msCreationsDate")
	private LocalDateTime msCreationsDate;

	public SendMailDTO() {
	}

	public SendMailDTO(int msId, String msSender, String msTo, String msCc, String msSubject, String msBody,
			String msError, int msStatus, LocalDateTime msCreationsDate) {
		this.msId = msId;
		this.msSender = msSender;
		this.msTo = msTo;
		this.msCc = msCc;
		this.msSubject = msSubject;
		this.msBody = msBody;
		this.msError = msError;
		this.msStatus = msStatus;
		this.msCreationsDate = msCreationsDate;
	}

	public int getMsId() {
		return msId;
	}

	public void setMsId(int msId) {
		this.msId = msId;
	}

	public String getMsSender() {
		return msSender;
	}

	public void setMsSender(String msSender) {
		this.msSender = msSender;
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

	public String getMsError() {
		return msError;
	}

	public void setMsError(String msError) {
		this.msError = msError;
	}

	public int getMsStatus() {
		return msStatus;
	}

	public void setMsStatus(int msStatus) {
		this.msStatus = msStatus;
	}

	public LocalDateTime getMsCreationsDate() {
		return msCreationsDate;
	}

	public void setMsCreationsDate(LocalDateTime msCreationsDate) {
		this.msCreationsDate = msCreationsDate;
	}

	@Override
	public String toString() {
		return "SendMailDTO [msId=" + msId + ", msSender=" + msSender + ", msTo=" + msTo + ", msCc=" + msCc
				+ ", msSubject=" + msSubject + ", msBody=" + msBody + ", msError=" + msError + ", msStatus=" + msStatus
				+ ", msCreationsDate=" + msCreationsDate + "]";
	}

}
