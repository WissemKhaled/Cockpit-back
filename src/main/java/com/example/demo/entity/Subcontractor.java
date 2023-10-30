package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subcontractor {
	private int SId;
	private String SName;
	private String SEmail;
	private Status SFkStatusId; // à modifier
													//s_fk_status_id
													//SFkStatusId
	public Subcontractor(String name, String email, Status SFkStatusId) {
		this.SName = name;
		this.SEmail = email;
		this.SFkStatusId = SFkStatusId;
	}

}
