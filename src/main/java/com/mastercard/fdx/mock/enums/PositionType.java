package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositionType {

	
	LONG("LONG"),
	SHORT("SHORT");
	
	private final String type;
	

	@JsonValue
	public String forJackson() {
		return type;
	}	
}
