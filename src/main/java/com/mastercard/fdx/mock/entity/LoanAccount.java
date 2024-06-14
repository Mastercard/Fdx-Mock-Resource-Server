package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mock_loan_accounts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class LoanAccount extends AccountDescriptor{


	private Double principalBalance;
	private Double escrowBalance;
	private Double originalPrincipal;
	private Date originatingDate;
	private int loanTerm;
	private int totalDoubleOfPayments;
	private Double nextPaymentAmount;
	private Date nextPaymentDate;
	private Double payOffAmount;
	private Double lastPaymentAmount;
	private Date lastPaymentDate;
	private Date maturityDate;
	private Double interestPaidYearToDate;	

}

