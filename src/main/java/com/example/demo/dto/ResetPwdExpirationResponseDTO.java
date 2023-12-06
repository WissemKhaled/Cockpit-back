package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetPwdExpirationResponseDTO {
	private String status;
    private String message;
    private boolean isValid;
    
    public ResetPwdExpirationResponseDTO(String status, String message){
    	this.status = status;
    	this.message = message;
    }
}
