package com.mastercard.fdx.mock.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class PaymentDetails implements Serializable {

	@Serial
	private static final long serialVersionUID = -2226176657991987434L;

	private Double principalAmount;

	private Double interestAmount;

	private Double insuranceAmount;
	
	private Double escrowAmount;

	private Double pmiAmount;

	private Double feesAmount;

}
