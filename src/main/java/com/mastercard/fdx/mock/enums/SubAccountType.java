package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubAccountType {

	CASH("CASH"),
	MARGIN("MARGIN"),
	OTHERS("OTHERS"),
	SHORT("SHORT"),
	OTHER("OTHER");

	private String type;
	
	@JsonValue
	public String forJackson() {
		return type;
	}	
}
