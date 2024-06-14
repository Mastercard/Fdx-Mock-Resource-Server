package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
	
	BUY("BUY"),
	BUY_TO_COVER("BUYTOCOVER"),
	BUY_TO_OPEN("BUYTOOPEN"),
	SELL("SELL"),
	SELL_CLOSE("SELLCLOSE"),
	SELL_SHORT("SELLSHORT"),
	SELL_TO_COVER("SELLTOCOVER"),
	SELL_TO_OPEN("SELLTOOPEN");

	private String type;

	@JsonValue
	public String forJackson() {
		return type;
	}
}
