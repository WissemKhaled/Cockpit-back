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
	private int Id;
	private String name;
	private String email;
	private SubcontractorStatus subcontractorStatus;

	public Subcontractor(String name, String email, SubcontractorStatus subcontractorStatus) {
		this.name = name;
		this.email = email;
		this.subcontractorStatus = subcontractorStatus;
	}

}
