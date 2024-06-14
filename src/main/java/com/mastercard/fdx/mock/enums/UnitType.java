package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum UnitType {
	
	CURRENCY("CURRENCY"),
	SHARES("SHARES");

	private String type;
	
	@JsonValue
	public String forJackson() {
		return type;
	}	

}
