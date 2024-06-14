package com.mastercard.fdx.mock.dto;

import com.mastercard.fdx.mock.enums.SecurityIdType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityId {

	private String id;
	private SecurityIdType idType;
}
