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
	private int sId;
	private String sName;
	private String sEmail;
	private int sFkStatusId; // à modifier
													//s_fk_status_id
													//SFkStatusId
	public Subcontractor(String sName, String sEmail, int sFkStatusId) {
		this.sName = sName;
		this.sEmail = sEmail;
		this.sFkStatusId = sFkStatusId;
	}

}
