package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class AccountHolderName {
	private String first;
	private String middle;
	private String last;
	private String company;
	private String prefix;
	private String suffix;

}
