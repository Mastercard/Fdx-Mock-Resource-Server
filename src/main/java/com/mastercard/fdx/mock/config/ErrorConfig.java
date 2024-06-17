package com.mastercard.fdx.mock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@PropertySource("classpath:error-config.properties")
@ConfigurationProperties("error")
public class ErrorConfig {

	private int accountNotFoundErrorCode;
	private String accountNotFoundErrorMsg;
	
	private int statementNotFoundErrorCode;
	private String statementNotFoundErrorMsg;
	
	
}
