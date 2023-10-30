package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubcontractorDto {
	private int sId;
	private String sName;
	private String sEmail;
	private int sFkStatusId;
}
