package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateGstLogDTO {
	@JsonProperty("logId")
	private int logId;
	
	@NotEmpty(message = "Le type de log ne doit pas être vide")
	@JsonProperty("logType")
	private String logType;
	
	@NotEmpty(message = "L'email ne doit pas être vide")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$",
	message = "Format d'email invalide"
	)
	@JsonProperty("logEmail")
	private String logEmail;
	
	@NotEmpty(message = "La valeur de log ne doit pas être vide")
	@JsonProperty("logValue")
	private String logValue;
	
	@JsonProperty("logCreationDate")
	private LocalDateTime logCreationDate;
}
