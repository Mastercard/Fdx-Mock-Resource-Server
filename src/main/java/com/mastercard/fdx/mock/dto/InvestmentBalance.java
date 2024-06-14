package com.mastercard.fdx.mock.dto;


import com.mastercard.fdx.mock.enums.BalanceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public class InvestmentBalance {

	private String balanceName;
	private String balanceDescription;
	private BalanceType balanceType;
	private Double balanceValue;
	private Calendar balanceDate;
	private Currency currency;
}
