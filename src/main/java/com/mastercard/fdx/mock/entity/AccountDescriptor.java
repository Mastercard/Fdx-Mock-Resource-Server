package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mastercard.fdx.mock.dto.ErrorPojo;
import com.mastercard.fdx.mock.enums.BalanceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "mock_accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonInclude(Include.NON_NULL)
public class AccountDescriptor extends ErrorPojo{

	@JsonIgnore
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")	
	private int accountId;
	
	private String accountCategory;
	
	@Column(unique = true)
	@JsonProperty("accountId")
	private String institutionAccountId;
	
	private String accountType;
	private String nickname;
	private String status;
	private Date balanceAsOf;
	private String accountNumber;
	private String accountNumberDisplay;
	private String productName;
	private String description;
	private Date accountOpenDate;
	private Date accountCloseDate;
	private String parentAccountId;
	private String lineOfBusiness;
	private String routingTransitNumber;
	private BalanceType balanceType;
	private Double interestRate;
	private String interestRateType;
	private Calendar interestRateAsOf;
	private Boolean transferIn;
	private Boolean transferOut;
	private String billPayStatus;
	private String micrNumber;
	private Calendar lastActivityDate;
	private String rewardProgramId;
	private Boolean transactionsIncluded;

	@Transient
	private Double currentBalance;
	@Transient
	private Double openingDayBalance;
	@Transient
	private Double principalBalance;
	@Transient
	private Double availableCashBalance;


	public AccountDescriptor() {
		super();
	}
	public AccountDescriptor(Integer errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
