package com.mastercard.fdx.mock.dto;

import java.util.List;

import com.mastercard.fdx.mock.entity.Statement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementsResponse extends ErrorPojo{

	private Page page;
	private Links links;
	private List<Statement> statements;
	
	
	public StatementsResponse() {
		super();
	}
	public StatementsResponse(Integer errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	
}
