package com.example.demo.builder;

import java.time.LocalDateTime;

import com.example.demo.entity.Category;
import com.example.demo.entity.MessageModel;

public class MessageModelBuilder {
	private Integer mmId;
	private Integer mmLink;
	private String mmSubject;
	private String mmBody;
	private Boolean mmHasEmail;
	private LocalDateTime mmCreationDate;
	private LocalDateTime mmLastUpdateDate;
	private Category category;

	public MessageModelBuilder withMmId(Integer mmId) {
		this.mmId = mmId;
		return this;
	}

	public MessageModelBuilder withMmLink(Integer mmLink) {
		this.mmLink = mmLink;
		return this;
	}

	public MessageModelBuilder withMmSubject(String mmSubject) {
		this.mmSubject = mmSubject;
		return this;
	}

	public MessageModelBuilder withMmBody(String mmBody) {
		this.mmBody = mmBody;
		return this;
	}

	public MessageModelBuilder withMmHasEmail(Boolean mmHasEmail) {
		this.mmHasEmail = mmHasEmail;
		return this;
	}

	public MessageModelBuilder withMmCreationDate(LocalDateTime mmCreationDate) {
		this.mmCreationDate = mmCreationDate;
		return this;
	}

	public MessageModelBuilder withMmLastUpdateDate(LocalDateTime mmLastUpdateDate) {
		this.mmLastUpdateDate = mmLastUpdateDate;
		return this;
	}

	public MessageModelBuilder withCategory(Category category) {
		this.category = category;
		return this;
	}

	public MessageModel build() {
		return new MessageModel(mmId, mmLink, mmSubject, mmBody, mmHasEmail, mmCreationDate, mmLastUpdateDate,
				category);
	}

}
