package com.mastercard.fdx.mock.entity;

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
@Table(name = "mock_lineOfCredit_accounts")
@JsonInclude(Include.NON_NULL)
public class LineOfCreditAccount extends AccountDescriptor{

	private Double creditLine;
	private Double availableCredit;
	private Double nextPaymentAmount;
	private Date nextPaymentDate;
	private Double principalBalance;
	private Double currentBalance;
	private Double minimumPaymentAmount;
	private Double lastPaymentAmount;
	private Date lastPaymentDate;
	private Double pastDueAmount;
	private Double lastStmtBalance;
	private Date lastStmtDate;
	private Double purchasesApr;
	private Double advancesAprl;
	private Double cashAdvanceLimit;
	private Double availableCash;
	private Double financeCharges;
	private String cardNetwork;
}

