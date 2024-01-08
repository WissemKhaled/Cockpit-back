package com.example.demo.builder;

import java.time.LocalDateTime;

import org.apache.catalina.User;

import com.example.demo.entity.SendMail;
import com.example.demo.entity.UUser;

public class SendMailBuilder {
	private int msId;
	private String msTo;
	private String msCc;
	private String msSubject;
	private String msBody;
	private LocalDateTime msSendDate;
	private int user;

	public SendMailBuilder withMsId(int msId) {
		this.msId = msId;
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

	public SendMailBuilder withMsSendDate(LocalDateTime msSendDate) {
		this.msSendDate = msSendDate;
		return this;
	}

	public SendMailBuilder withMsUser(int user) {
		this.user = user;
		return this;
	}
	
	public SendMail build() {
		return new SendMail(msId,msTo,msCc,msSubject,msBody,msSendDate,user);
	}

}
