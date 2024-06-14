package com.mastercard.fdx.mock.interceptor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMethodUriToken {

	private RequestMethodUri requestMethodUri;
	private String tokenName;
}
