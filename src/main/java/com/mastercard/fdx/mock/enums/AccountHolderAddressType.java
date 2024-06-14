package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountHolderAddressType {

	MAILING("MAILING"),
	MAIN("MAIN"),
	STATEMENT("STATEMENT"),
	PREVIOUS("PREVIOUS"),
	BUSINESS("BUSINESS"),
	HOME("HOME");
	
	private String value;
	
	@JsonValue
	@Override
	public String toString() {
		return this.value;
	}
}
