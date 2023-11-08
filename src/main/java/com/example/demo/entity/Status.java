package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Status {
	@NotNull
	@Max(value = 4, message = "L'id doit être inférieure ou égale à 4")
	@Min(value = 1, message = "L'id doit être supérieure ou égale à 1")
	@JsonProperty("stId")
	private int stId;

//	@NotEmpty(message = "Le nom du statut est nécessaire")
	@JsonProperty("stName")
	private String stName;

//	@NotEmpty(message = "Le nom du statut est nécessaire")
	@JsonProperty("stDescription")
	private String stDescription;

}
