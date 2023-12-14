package com.example.demo.entity.builder;

import java.time.LocalDateTime;

import com.example.demo.entity.SendMail;

public class SendMailBuilder {
	private int msId;
	private String msSender;
	private String msTo;
	private String msCc;
	private String msSubject;
	private String msBody;
	private String msError;
	private int msStatus;
	private LocalDateTime msCreationsDate;

	public SendMailBuilder withMsId(int msId) {
		this.msId = msId;
		return this;
	}

	public SendMailBuilder withMsSender(String msSender) {
		this.msSender = msSender;
		return this;
	}

	public SendMailBuilder withMsTo(String msTo) {
		this.msTo = msTo;
		return this;
	}

	public SendMailBuilder withMsCc(String msCc) {
		this.msCc = msCc;
		return this;
	}

	public SendMailBuilder withMsSubject(String msSubject) {
		this.msSubject = msSubject;
		return this;
	}

	public SendMailBuilder withMsBody(String msBody) {
		this.msBody = msBody;
		return this;
	}

	public SendMailBuilder withMsError(String msError) {
		this.msError = msError;
		return this;
	}

	public SendMailBuilder withMsStatus(int msStatus) {
		this.msStatus = msStatus;
		return this;
	}

	public SendMailBuilder withMsCreationsDate(LocalDateTime msCreationsDate) {
		this.msCreationsDate = msCreationsDate;
		return this;
	}
	
	public SendMail build() {
		return new SendMail(msId,msSender,msTo,msCc,msSubject,msBody,msError,msStatus,msCreationsDate);
	}

}
