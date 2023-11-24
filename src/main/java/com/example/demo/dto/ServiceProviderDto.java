package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceProviderDto {
	@JsonProperty("spId")
	private int spId;

	@NotEmpty(message = "le prénom ne doit pas étre null")
	@Size(min = 2, max = 250, message = "la longueur du prénom doit etre entre 2 et 25O caractères")
	@Pattern(regexp = "^[a-zA-Z]{2,}$", message = "Le format du prénom saisi est invalide")
	@JsonProperty("spFirstName")
	private String spFirstName;
	
	@NotEmpty(message = "le nom ne doit pas étre null")
	@Size(min = 2, max = 250, message = "la longueur du nom doit etre entre 2 et 25O caractères")
	@Pattern(regexp = "^[a-zA-Z]{2,}$", message = "Le format du nom saisi est invalide")
	@JsonProperty("spName")
	private String spName;
	
	@NotEmpty(message = "l'email ne doit pas étre null")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("spEmail")
	private String spEmail;

	@JsonProperty("spCreationDate")
	private LocalDateTime spCreationDate;

	@JsonProperty("spLastUpdateDate")
	private LocalDateTime spLastUpdateDate;
	
	@Max(value = 4, message = "L'id du statut doit être inférieure ou égale à 4")
	@Min(value = 1, message = "L'id du statut doit être supérieure ou égale à 1")
	@JsonProperty("spStatusId")
	private int spStatutId;

	@Positive(message = "l'id du sous-traitant doit être un nombre strictement positif")
	@JsonProperty("subcontractorId")
	private int subcontractorId;
}
