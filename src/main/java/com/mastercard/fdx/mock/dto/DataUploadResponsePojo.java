package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class DataUploadResponsePojo extends ErrorPojo{

	private Integer code;
	private String message;
	public DataUploadResponsePojo(Integer errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	public DataUploadResponsePojo() {
		super();
	}
	
	
}
