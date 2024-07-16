package com.mastercard.fdx.mock.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.mastercard.fdx.mock.entity.Statement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementsResponse extends ErrorPojo implements Serializable {

	@Serial
	private static final long serialVersionUID = -9183473935775161077L;

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
