package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Inv401kSource {
	AFTER_TAX("AFTERTAX"),
	MATCH("MATCH"),
	OTHER_NON_VEST("OTHERNONVEST"),
	OTHER_VEST("OTHERVEST"),
	PRE_TAX("PRETAX"),
	PROFIT_SHARING("PROFITSHARING"),
	ROLLOVER("ROLLOVER");
	
	private String type;
	
	@JsonValue
	public String forJackson() {
		return type;
	}	
}
