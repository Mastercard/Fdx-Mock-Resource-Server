package com.mastercard.fdx.mock.exception;

import org.springframework.http.HttpStatusCode;

import com.mastercard.fdx.mock.dto.ErrorPojo;

import lombok.Getter;

@Getter
public class ApiException extends Exception{
	private final HttpStatusCode code;
	private final ErrorPojo errorPojo;
	public ApiException (HttpStatusCode code,ErrorPojo errorPojo ,String msg) {
		super(msg);
		this.code = code;
		this.errorPojo=errorPojo;
	}
}
