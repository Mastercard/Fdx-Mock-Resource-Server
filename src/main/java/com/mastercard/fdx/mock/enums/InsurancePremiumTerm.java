package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InsurancePremiumTerm {

	ANNUAL("ANNUAL"),
	MONTHLY("MONTHLY");
	
	private String premiumTerm;
	

	@JsonValue
	public String forJackson() {
		return premiumTerm;
	}	

}
