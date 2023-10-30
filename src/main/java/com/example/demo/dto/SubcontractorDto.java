package com.example.demo.dto;

import com.example.demo.entity.SubcontractorStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubcontractorDto {
	private int Id;
	private String name;
	private String email;
	private SubcontractorStatus subcontractorStatus;
}
