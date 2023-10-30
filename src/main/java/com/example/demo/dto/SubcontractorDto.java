package com.example.demo.dto;

import com.example.demo.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubcontractorDto {
	private int SId;
	private String SName;
	private String SEmail;
	private Status SFkStatusId;
}
