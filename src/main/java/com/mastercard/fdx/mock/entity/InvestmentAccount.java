package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mock_investment_accounts")
@JsonInclude(Include.NON_NULL)
public class InvestmentAccount extends AccountDescriptor implements Serializable{

	@Serial
	private static final long serialVersionUID = -7743529580387654009L;
	private boolean allowedCheckWriting;
	private boolean allowedOptionTrade;
	private Double currentValue;
	
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "account_id")
	private List<Holding> holdings;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "account_id")
	private List<OpenOrder> openOrders;
	
	private Double availableCashBalance;
	private boolean margin;
	private Double marginBalance;
	private Double shortBalance;
	private Double rolloverAmount;
	private String employerName;
	private String brokerId;
	private String planId;
	private int calendarYearFor401K;
	private Double dailyChange;
	private Double percentageChange;

}
