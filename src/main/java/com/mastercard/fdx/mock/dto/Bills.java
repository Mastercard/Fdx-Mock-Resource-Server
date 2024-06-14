package com.mastercard.fdx.mock.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bills {

	private Number totalPaymentDue;
	private Number minimumPaymentDue;
	private Date dueDate;
	private boolean autoPayEnabled;
	private Number autoPayAmount;
	private Date autoPayDate;
	private Number pastDueAmount;
	private Number lastPaymentAmount;
	private Date lastPaymentDate;
	private Number statementBalance;
	private Date statementDate;
}
