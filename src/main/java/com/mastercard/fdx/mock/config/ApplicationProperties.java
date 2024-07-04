package com.mastercard.fdx.mock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class ApplicationProperties {

	@Value("${mock.res.server.auth.code}")
	private String resourceServerAuthCode;
	
	@Value("${mock.auth.server.url}")
	private String authServerBaseUrl;
	
	@Value("${mock.auth.server.jwks.uri.path}")
	private String authServerJwksUriPath;

	@Value("${mock.auth.issuer.url}")
	private String issuerUrl;
}
