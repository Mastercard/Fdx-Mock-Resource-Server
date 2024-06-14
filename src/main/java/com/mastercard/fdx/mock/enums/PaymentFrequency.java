package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentFrequency {

	DAILY("DAILY"),
	WEEKLY("WEEKLY"),
	BIWEEKLY("BIWEEKLY"),
	SEMIMONTHLY("SEMIMONTHLY"),
	MONTHLY("MONTHLY"),
	SEMIANNUALLY("SEMIANNUALLY"),
	ANNUALLY("ANNUALLY");

	private String type;
	@JsonValue
	public String forJackson() {
		return type;
	}	

	
	
}
