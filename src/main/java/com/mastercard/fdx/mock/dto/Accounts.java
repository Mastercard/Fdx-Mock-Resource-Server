package com.mastercard.fdx.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Accounts {

	private Page page;
	private Links links;
	private List< ? extends AccountListResponsePojo> accounts;
}
