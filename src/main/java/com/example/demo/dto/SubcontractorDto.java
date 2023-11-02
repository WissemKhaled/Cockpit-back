package com.example.demo.dto;


import com.example.demo.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubcontractorDto {


			@JsonProperty("sId")
			private int sId;
			
			@JsonProperty("sName")
			private String sName;
			
			@JsonProperty("sEmail")
			private String sEmail;
			
			@JsonProperty("sFkStatusId")
			private int sFkStatusId;

}
