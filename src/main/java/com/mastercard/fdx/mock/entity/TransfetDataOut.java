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

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Embeddable
public class TransfetDataOut implements Serializable {

	@Serial
	private static final long serialVersionUID = -8996783511948836812L;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferOut_day_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferOut_day_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferOut_day_transferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferOut_day_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferOut_day_remainingOccurrence")),
	  })
	private TransferDetails day;

	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferOut_week_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferOut_week_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferOut_week_transferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferOut_week_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferOut_week_remainingOccurrence")),
	  })
	private TransferDetails week;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferOut_month_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferOut_month_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferOut_month_transferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferOut_month_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferOut_month_remainingOccurrence")),
	  })
	private TransferDetails month;


	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="resetsOn",column=@Column(name="transferOut_year_resetsOn")),
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferOut_year_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferOut_year_trnsferRemainingAmount")),
	    @AttributeOverride(name="maxOccurrence",column=@Column(name="transferOut_year_maxOccurrence")),
	    @AttributeOverride(name="remainingOccurrence",column=@Column(name="transferOut_year_reainingOccurrence")),
	  })
	private TransferDetails year;	
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="transferMaxAmount",column=@Column(name="transferOut_transaction_transferMaxAmount")),
	    @AttributeOverride(name="transferRemainingAmount",column=@Column(name="transferOut_transaction_trnsferRemainingAmount")),
	  })
	private TransferDetails transaction;	
}
