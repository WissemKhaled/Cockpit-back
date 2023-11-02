package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Subcontractor {

	private int sId;
	private String sName;
	private String sEmail;
	private int sFkStatusId; 
	
	
	public Subcontractor(String sName, String sEmail, int sFkStatusId) {
		this.sName = sName;
		this.sEmail = sEmail;
		this.sFkStatusId = sFkStatusId;
	}


}
