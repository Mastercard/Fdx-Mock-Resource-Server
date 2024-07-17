package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;

@Getter
@Setter
@Embeddable 
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AccountHolders implements Serializable {

	@Serial
	private static final long serialVersionUID = -1648217302019372702L;

	private String relationship;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("type")
	private String type;
	
	@Embedded
	@JsonProperty("name") 
	private AccountHolderName customerName;
	 
	@JsonProperty("dateOfBirth") 
	private String dateOfBirth;
	
	@JsonProperty("taxId") 
	private String taxId;
	
	@JsonProperty("taxIdCountry") 
	private String taxIdCountry;
	
	@JsonProperty("governmentId") 
	private String governmentId;
	
	@JsonProperty("customerStartDate")
	private Calendar customerStartDate;

	@JsonProperty("lastActivityDate") 
	private String lastActivityDate;
}
