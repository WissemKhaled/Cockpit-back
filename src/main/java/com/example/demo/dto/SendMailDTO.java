package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.MessageModel;
import com.example.demo.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SendMailDTO {
	
	@JsonProperty("msId")
	private int msId;
	
	@JsonProperty("msSender")
	private String msSender;
	
	@NotNull
	@NotEmpty(message = "l'email est nécessaire")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("msTo")
	private String msTo;
	
	@NotEmpty(message = "l'email est nécessaire")
	@Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z\\d]*[-._]?[a-zA-Z\\d]+)@[a-zA-Z\\d]+[-._]?[a-zA-Z\\d]+\\.[a-zA-Z]{2,3}$", message = "Le format de l'email est invalide")
	@JsonProperty("msCc")
	private String msCc;
	
	@NotEmpty(message = "le sujet est nécessaire")
	@JsonProperty("msSubject")
	private String msSubject;
	
	@NotEmpty(message = "le corps du mail est nécessaire")
	@JsonProperty("msBody")
	private String msBody;
	
	@JsonProperty("msError")
	private String msError;
	
	@Max(value = 3, message = "L'id doit être inférieure ou égale à 3")
	@Min(value = 1, message = "L'id doit être supérieure ou égale à 1")
	@JsonProperty("msStatus")
	private int msStatus;
	
	@JsonProperty("msCreationsDate")
	private LocalDateTime msCreationsDate;
	
	@Valid
	@JsonProperty("messageModel")
	private MessageModel messageModel;

}
