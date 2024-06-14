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
@Table(name = "mock_deposit_accounts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class DepositAccount extends AccountDescriptor{
	
	private Double currentBalance;
	private Double openingDayBalance;
	private Double availableBalance;
	private Double annualPercentageYield;
	private Double interestYtd;
	private int term;
	private Date maturityDate;
	
	
	
	
}
