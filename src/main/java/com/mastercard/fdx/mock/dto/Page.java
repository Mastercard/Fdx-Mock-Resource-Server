package com.mastercard.fdx.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Page implements Serializable {

	@Serial
	private static final long serialVersionUID = 7586660968389909319L;

	private String nextOffset;
	private int total;
}
