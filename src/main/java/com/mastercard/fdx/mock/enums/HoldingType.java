package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HoldingType {
	
	ANNUITY("ANNUITY"),
	BOND("BOND"),
	CD("CD"),
	MUTUAL_FUND("MUTUALFUND"),
	OPTIONS("OPTIONS"),
	STOCK("STOCK"),

	CASH_CASHEQUIVALENTS("CASH/CASHEQUIVALENTS"),
	EQUITIES("EQUITIES"),
	MUTUAL_FUNDS("MUTUALFUNDS"),
	FIXED_INCOME("FIXEDINCOME"),
	OPTION("OPTION"),
	REALASSETS("REALASSETS"),
    OTHER("OTHER"),
	DIGITALASSET("DIGITALASSET");

	
	private String type;

	@JsonValue
	public String forJackson() {
		return type;
	}	
}
