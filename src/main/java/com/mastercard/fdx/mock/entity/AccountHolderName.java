package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class AccountHolderName implements Serializable {
	@Serial
	private static final long serialVersionUID = -4752646932100799574L;

	private String first;
	private String middle;
	private String last;
	private String company;
	private String prefix;
	private String suffix;

}
