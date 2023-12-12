package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
	@JsonProperty("uId")
	private int uId;
	
	@NotEmpty(message = "L'email ne doit pas être vide")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$",
			message = "Format d'email invalide"
			)
	@JsonProperty("uEmail")
	private String uEmail;
	
	@NotEmpty(message = "Le mot de passe ne doit pas être vide")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?_\\-])[A-Za-z\\d@$!%*?_\\-]{6,}$",
			 message = "Format du mot de passe invalide"
    )
	@JsonProperty("uPassword")
	private String uPassword;
	
	@JsonProperty("uFirstName")
	private String uFirstName;
	
	@JsonProperty("uLastName")
	private String uLastName;
	
	@JsonProperty("uStatus")
	private boolean uStatus;
	
	@JsonProperty("insertionDate")
	private LocalDateTime insertionDate;
	
	@JsonProperty("lastUpdate")
	private LocalDateTime lastUpdate;
}
