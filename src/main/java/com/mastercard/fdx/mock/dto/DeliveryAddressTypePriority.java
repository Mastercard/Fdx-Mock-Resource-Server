package com.mastercard.fdx.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum DeliveryAddressTypePriority {

	MAILING(1, "MAILING"),
	MAIN(2, "MAIN"),
	STATEMENT(3, "STATEMENT"),
	PREVIOUS(4, "PREVIOUS"),
	BUSINESS(5, "BUSINESS"),
	HOME(6, "HOME");
	
	private int priority;
	private String type;
	
	public String getValue() {
		return type;
	}
	

	
}
