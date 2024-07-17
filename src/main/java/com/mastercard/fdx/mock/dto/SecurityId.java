package com.mastercard.fdx.mock.dto;

import com.mastercard.fdx.mock.enums.SecurityIdType;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class SecurityId implements Serializable {

	@Serial
	private static final long serialVersionUID = 2120981650031173282L;

	private String id;
	private SecurityIdType idType;
}
