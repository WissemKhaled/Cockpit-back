package com.example.demo.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken {

	@JsonProperty("rtId")
	private int rtId;

	@JsonProperty("rtToken")
	private String rtToken;

	@JsonProperty("rtExpiryDate")
	private Instant rtExpiryDate;

	@JsonProperty("uUser")
	private UUser uUser;

}
