package com.mastercard.fdx.mock.transaction.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "mock_investment_transactions_details")
@JsonInclude(Include.NON_NULL)
public class InvestmentTransaction extends TransactionsDetails implements Serializable {

	@Serial
	private static final long serialVersionUID = -5846801446702648111L;

	private Double shares;

	private Double faceValue;

	private Double price;

	private String securityId;

	private String securityIdType;

	private String securityType;

	private String symbol;

	private Double markup;

	private Double commission;

	private Double taxes;

	private Double fees;

	private Double load;

	@JsonProperty(value = "inv401kSource")
	private String inv401kSourceType;

	private String confirmationNumber;

	private Double fractionalCash;

	private String incomeType;

	private Double oldUnits;

	private Double splitRatioNumerator;

	private Double splitRatioDenominator;

	private Double newUnits;

	private String subAccountSec;

	private String subAccountFund;

	private String loanId;

	private Double loanPrincipal;

	private Double loanInterest;

	private Calendar payrollDate;

	private Boolean priorYearContrib;

	private Double withholding;

	private Boolean taxExempt;

	private Double gain;

	private Double stateWithholding;

	private Double penalty;

	private Double runningBalance;

	private Double unitPrice;

	private Double units;

	private String unitType;

	private String transactionReason;
	
	private Double accruedInterest;
	
	private String transferAction;
	
	private String positionType;

	private String ticker;
}
