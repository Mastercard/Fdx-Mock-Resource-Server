package com.mastercard.fdx.mock.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class PaymentDetails {

	private Double principalAmount;

	private Double interestAmount;

	private Double insuranceAmount;
	
	private Double escrowAmount;

	private Double pmiAmount;

	private Double feesAmount;

}
