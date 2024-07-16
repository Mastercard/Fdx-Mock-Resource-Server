package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelephoneNumber implements Serializable {

	@Serial
	private static final long serialVersionUID = 8139286700243032207L;

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("number")
	private String number;
}
