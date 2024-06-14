package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderDuration {
	
	DAY("DAY"),
	GOOD_TILL_CANCEL("GOODTILLCANCEL"),
	IMMEDIATE("IMMEDIATE");
	
	private String type;
	
	@JsonValue
	public String forJackson() {
		return type;
	}
}
