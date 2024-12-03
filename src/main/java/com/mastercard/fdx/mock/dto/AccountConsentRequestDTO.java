package com.mastercard.fdx.mock.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountConsentRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private List<String> accountIds;
	
	private long consentShareDurationSeconds;
}
