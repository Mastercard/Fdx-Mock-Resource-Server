package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "payment_networks")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AccountPaymentNetwork implements Serializable {

	@Serial
	private static final long serialVersionUID = -8342276781234148016L;

	@JsonIgnore
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")	
	private int id;
	
	private String accountId;
	
	private String bankId;
	private String identifier;
	private String identifierType;
	private String type;
	private boolean transferIn;
	private boolean transferOut;
	private boolean supportsRequestForPayment;
	@Embedded
	private TransferLimits transferLimits;
}
