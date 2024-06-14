package com.mastercard.fdx.mock.dto;

import lombok.Getter;
import lombok.Setter;
import com.mastercard.fdx.mock.enums.SecurityIdType;

@Getter
@Setter
public class Contribution {

	private String securityId;
	private SecurityIdType securityIdType;
	private Double employerMatchPercentage;
	private Double employerMatchAmount;
	private Double employeePreTaxAmount;
	private Double employeePreTaxPercentage;
	private Double employeeAfterTaxAmount;
	private Double employeeAfterTaxPercentage;
	private Double employeeDeferPreTaxAmount;
	private Double employeeDeferPreTaxPercentage;
	private Double employeeYearToDate;
	private Double employerYearToDate;
	private Double rolloverContributionPercentage;
	private Double rolloverContributionAmount;
}
