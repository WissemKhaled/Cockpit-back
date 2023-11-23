package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateMessageModelDTO {
	@JsonProperty("mmId")
	private int mmId;
	
	@NotEmpty(message = "Le type de message ne doit pas être vide")
	@JsonProperty("mmType")
	private String mmType;
	
	@NotEmpty(message = "Le sujet du modèle ne doit pas être vide")
	@JsonProperty("mmSubject")
	private String mmSubject;
	
	@NotEmpty(message = "Le contenu du modèle ne doit pas être vide")
	@JsonProperty("mmModel")
	private String mmModel;
	
	@JsonProperty("mmCreationDate")
	private LocalDateTime mmCreationDate;
	
	@JsonProperty("mmLastUpdate")
	private LocalDateTime mmLastUpdate;
}
