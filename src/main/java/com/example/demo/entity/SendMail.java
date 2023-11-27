package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class SendMail {
	
	@JsonProperty("msId")
	private int msId;
	
	@JsonProperty("msSender")
	private String msSender;
	
	@JsonProperty("msRecipient")
	private String msRecipient;
	
	@JsonProperty("msCreationsDate")
	private LocalDateTime msCreationsDate;
	
	@JsonProperty("messageModel")
	private MessageModel messageModel;

}
