package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HoldingSubType {

	CASH("CASH"),
	MONEY_MARKET("MONEYMARKET");

	private String type;
	
	@JsonValue
	public String forJackson() {
		return type;
	}
}
