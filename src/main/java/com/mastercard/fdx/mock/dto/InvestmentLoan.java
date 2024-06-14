package com.mastercard.fdx.mock.dto;


import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestmentLoan {

	private String loanId;
	private String loanDescription;
	private Double initialLoanBalance;
	private Calendar loanStartDate;
	private Double currentLoanBalance;
	private Calendar dateAsOf;
	private Double loanRate;
	private Double loanPaymentAmount;
	private String loanPaymentFrequency;
	private Double loanPaymentInitial;
	private Integer loanPaymentsRemaining;
	private Calendar loanMaturityDate;
	private Double loanInterestToDate;
	private Double loanTotalProjectedInterest;
	private Calendar loanNextPaymentDate;
}
