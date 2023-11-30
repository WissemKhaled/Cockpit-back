package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

	@NotEmpty(message = "le nom est nécessaire")
	@Size(min = 2, max = 250, message = "la longueur du nom doit etre entre 2 et 25O caractères")
	@JsonProperty("sName")
	private String sName;

	@NotEmpty(message = "l'email est nécessaire")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("sEmail")
	private String sEmail;

	@JsonProperty("sCreationDate")
	private LocalDateTime sCreationDate;

	@JsonProperty("sLastUpdateDate")
	private LocalDateTime sLastUpdateDate;
	
	@Valid
	@JsonProperty("status")
	private Status status;
	
	@JsonProperty("serviceProvidersIds")
	private List<Integer> serviceProvidersIds;

}
