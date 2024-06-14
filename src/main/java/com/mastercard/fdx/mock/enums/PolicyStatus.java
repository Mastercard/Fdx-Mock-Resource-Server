package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PolicyStatus {

	ACTIVE("ACTIVE"),
	DEATH_CLAIM_PAID("DEATH_CLAIM_PAID"),
	DEATH_CLAIM_PENDING("DEATH_CLAIM_PENDING"),
	EXPIRED("EXPIRED"),
	GRACE_PERIOD("GRACE_PERIOD"),
	LAPSE_PENDING("LAPSE_PENDING"),
	TERMINATED("TERMINATED"),
	WAIVER("WAIVER");

	private String policyStatus;
	@JsonValue
	public String forJackson() {
		return policyStatus;
	}	

	
	
}
