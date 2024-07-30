package com.mastercard.fdx.mock.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestInterceptorTest {

	public static final String VALID_URL = "/user";
	public static final String INVALID_URL = "/invalid/url";
	@InjectMocks
	private RequestInterceptor interceptor;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private Object handler;

	@BeforeEach
	void init() {
		ReflectionTestUtils.setField(interceptor, "excludedUrls", Arrays.asList(
				"/swagger-ui.html,/swagger-resources,/webjars/springfox-swagger-ui/,/v3/api-docs,/csrf,/health,/error"
						.split(",")));
		ReflectionTestUtils.setField(interceptor, "includedUrls", Collections.emptyList());
		ReflectionTestUtils.setField(interceptor, "listOfUniqueTokenSet", new HashSet<>(
				Arrays.asList("testGeneralToken123,testExternalToken123,testKeyToken123".split(","))));
		ReflectionTestUtils.setField(interceptor, "securityInterceptConfig", getSecurityInterceptConfig());
	}

	@Test
	void doFilterForValidUrl() {
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("testGeneralToken123");
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn(VALID_URL);
		Assertions.assertTrue(interceptor.preHandle(request, response, handler));
	}

	@Test
	void doFilterForExcludeUrl() {
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/");
		Assertions.assertTrue(interceptor.preHandle(request, response, handler));

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/health");
		Assertions.assertTrue(interceptor.preHandle(request, response, handler));

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/swagger-ui.html");
		Assertions.assertTrue(interceptor.preHandle(request, response, handler));

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/error");
		Assertions.assertTrue(interceptor.preHandle(request, response, handler));

	}

	@Test
	void doFilterForTokenNotPresentInRequestForSecuredEndpoint() {
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn(VALID_URL);
		Assertions.assertThrows(SecurityException.class, (() -> interceptor.preHandle(request, response, handler)),
				"Request validation failed. Either authorization code is blank or invalid.");
	}

	@Test
	void doFilterForInvalidTokenPresentInRequestForSecuredEndpoint() {
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("testGeneralToken12345");
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn(VALID_URL);
		Assertions.assertThrows(SecurityException.class, (() -> interceptor.preHandle(request, response, handler)),
				"Request validation failed. Either authorization code is blank or invalid.");
	}

	@Test
	void doFilterForInvalidUrl() {
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("testGeneralToken123");
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn(INVALID_URL);
		Assertions.assertThrows(SecurityException.class, (() -> interceptor.preHandle(request, response, handler)),
				"Invalid Url");
	}

	private SecurityInterceptConfig getSecurityInterceptConfig() {
		String generalToken = "testGeneralToken123";
		Map<RequestMethodUri, String> map = new HashMap<>();
		map.put(new RequestMethodUri("GET", VALID_URL), generalToken);

		SecurityInterceptConfig config = new SecurityInterceptConfig();
		ReflectionTestUtils.setField(config, "requestMethodUriTokenMapping", map);
		return config;
	}
}
