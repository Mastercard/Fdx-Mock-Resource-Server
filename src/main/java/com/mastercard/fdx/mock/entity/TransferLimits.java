package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TransferLimits {

	  @AttributeOverrides({
	    @AttributeOverride(name="day",column=@Column(name="transfer_out_day")),
	    @AttributeOverride(name="week",column=@Column(name="transfer_out_week")),
	    @AttributeOverride(name="month",column=@Column(name="transfer_out_month")),
	    @AttributeOverride(name="year",column=@Column(name="transfer_out_year")),
	
	  })
	@Embedded
	private TransfetDataOut out;
}
