package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class TelephoneNumber {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("number")
	private String number;
}
