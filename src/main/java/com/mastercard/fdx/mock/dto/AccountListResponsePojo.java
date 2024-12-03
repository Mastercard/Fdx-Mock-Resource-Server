package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AccountListResponsePojo extends ErrorPojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accountCategory;

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
	private Double currentBalance;
	private Double openingDayBalance;
	private Double principalBalance;
	private Double availableCashBalance;
	
}
