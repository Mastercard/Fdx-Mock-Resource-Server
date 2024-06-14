package com.mastercard.fdx.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {

	private Number currencyRate;
	private String currencyCode;
	private String originalCurrencyCode;
}
