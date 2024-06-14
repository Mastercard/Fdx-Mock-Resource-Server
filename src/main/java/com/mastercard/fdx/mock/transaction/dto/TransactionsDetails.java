package com.mastercard.fdx.mock.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mastercard.fdx.mock.dto.PaymentDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mock_transactions")
public class TransactionsDetails {

	@JsonIgnore
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;

	private String accountCategory;
	
	private String accountId;

	private String transactionId;
	
	private String transactionType;

	private String referenceTransactionId;

	private Calendar postedTimestamp;

	private Calendar transactionTimestamp;

	private String description;

	private String memo;

	private String debitCreditMemo;

	private String category;

	private String subCategory;

	private String reference;

	private String status;

	private Double amount;

	private Double foreignAmount;

	private String foreignCurrency;

	private List<String> imageIds;
	
	private String payee;

	private Integer checkNumber;
	
	@Embedded
	PaymentDetails paymentDetails;

}
