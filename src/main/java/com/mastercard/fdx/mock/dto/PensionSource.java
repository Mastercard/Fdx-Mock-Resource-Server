package com.mastercard.fdx.mock.dto;

import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PensionSource {

	private String displayName;
	private double amount;
	private String paymentOption;
	private Calendar asOfDate;
	private String frequency;
	private Calendar startDate;
}
