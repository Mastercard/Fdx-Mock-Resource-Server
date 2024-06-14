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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Embeddable
public class TransfetDataIn {

	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferIn_day_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferIn_day_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferIn_day_transferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferIn_day_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferIn_day_remainingOccurrence")),
	  })
	private TransferDetails day;
	
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferIn_week_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferIn_week_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferIn_week_transferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferIn_week_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferIn_week_remainingOccurrence")),
	  })
	private TransferDetails week;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferIn_month_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferIn_month_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferIn_month_transferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferIn_month_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferIn_month_remainingOccurrence")),
	  })
	private TransferDetails month;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferIn_year_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferIn_year_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferIn_year_trnsferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferIn_year_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferIn_year_reainingOccurrence")),
	  })
	private TransferDetails year;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferIn_transaction_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferIn_transaction_trnsferRemainingAmount")),
	  })
	private TransferDetails transaction;	
}
