package com.mastercard.fdx.mock.interceptor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SecurityInterceptConfigTest {
	
	@InjectMocks
	private SecurityInterceptConfig securityInterceptConfig;
	
	List<RequestMethodUriToken> requestMethodUriTokens;
	
	@BeforeEach
	void init() {
		securityInterceptConfig = new SecurityInterceptConfig();
		RequestMethodUri requestMethodUri = new RequestMethodUri("GET", "/v1/testURI");
		RequestMethodUriToken requestMethodUriToken = new RequestMethodUriToken();
		requestMethodUriToken.setRequestMethodUri(requestMethodUri);
		requestMethodUriToken.setTokenName("generalTest123");
		
		requestMethodUriTokens = new ArrayList<>();
		requestMethodUriTokens.add(requestMethodUriToken);
		ReflectionTestUtils.setField(securityInterceptConfig, "requestMethodUriTokens", requestMethodUriTokens);

	}

	@Test
	void testGetTokenPositive() {
		securityInterceptConfig.initMapping();
		RequestMethodUri requestMethodUri = new RequestMethodUri("GET", "/v1/testURI");		
		Assertions.assertNotNull(securityInterceptConfig.getToken(requestMethodUri));
	}
	
	@Test
	void testGetTokenNegative() {
		securityInterceptConfig.initMapping();
		RequestMethodUri requestMethodUri = new RequestMethodUri("GET", "/v1/testURI");		
		Assertions.assertNotNull(securityInterceptConfig.getToken(requestMethodUri));
	}
	
	@Test
	void testGetTokenNegativeWhenMappingAbsent() {
		requestMethodUriTokens =null;
		ReflectionTestUtils.setField(securityInterceptConfig, "requestMethodUriTokens", requestMethodUriTokens);
		securityInterceptConfig.initMapping();
		RequestMethodUri requestMethodUri = new RequestMethodUri("GET", "/v1/testURI");		
		Assertions.assertNull(securityInterceptConfig.getToken(requestMethodUri));
	}

}
