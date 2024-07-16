package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TransferDetails implements Serializable {

	@Serial
	private static final long serialVersionUID = 5114506561015706796L;

	private String resetsOn;

	private double transferMaxAmount;

	private double transferRemainingAmount;

	private int maxOccurrence;

	private int remainingOccurrence;
}