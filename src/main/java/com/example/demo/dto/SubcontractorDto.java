package com.example.demo.dto;

import com.example.demo.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubcontractorDto {
    @JsonProperty("sId")
	private int sId;
    
    @JsonProperty("sName")
	private String sName;
    
    @JsonProperty("sEmail")
	private String sEmail;
    
    @JsonProperty("status")
	private Status status;
}
