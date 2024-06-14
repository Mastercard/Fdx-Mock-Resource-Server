package com.mastercard.fdx.mock.enums;

import lombok.Getter;

@Getter
public enum SecurityIdType {

	CINS("CINS"),
	CMC("CMC"),
	CME("CME"),
	CUSIP("CUSIP"),
	ISIN("ISIN"), 
	ITSA("ITSA"),
	NASDAQ("NASDAQ"),
	SEDOL("SEDOL"),
	SICC("SICC"),
	VALOR("VALOR"),
	WKN("WKN");
	
	
	private String idType;
	
	SecurityIdType(String type) {
		this.idType=type;
	}

}
