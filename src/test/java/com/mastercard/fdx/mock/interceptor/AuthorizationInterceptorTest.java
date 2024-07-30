package com.mastercard.fdx.mock.interceptor;

import com.mastercard.fdx.mock.config.ApplicationProperties;
import com.mastercard.fdx.mock.utilities.JwksUtil;
import com.mastercard.fdx.mock.utilities.RemoteJWKSSetHelper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
class AuthorizationInterceptorTest {

	public static final String VALID_URL = "/fdx/v6/customer/detail";
	public static final String TEST_EXCLUDE_URL1 = "/fdx/v6/testexclude1";
	public static final String TEST_EXCLUDE_URL2 = "/fdx/v6/testexclude2";
	public static final String TEST_EXCLUDE_URLS = TEST_EXCLUDE_URL1 + "," + TEST_EXCLUDE_URL2;
	public static final String[] FDX_VALID_SCOPES = {
			"client.create",
			"client.read",
			"fdx:transactions:read",
			"fdx:accountbasic:read",
			"openid",
			"fdx:customerpersonal:read"
			};
	public static final String VALID_DATAHOLDER_ISSUER = "VALID_DATAHOLDER_ISSUER";
	public static final String VALID_TEST_CONSENT_ID = "VALID_TEST_CONSENT_ID";
	public static final int DEFAULT_ACCESS_DURATION = 3600;

	public static final String FIN_REQ = "TestFinReq";

	@InjectMocks
	private AuthorizationInterceptor interceptor;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	ApplicationProperties appProps;

	@Mock
	RemoteJWKSSetHelper remoteJWKSSetHelper;

	@Mock
	private Object handler;

	@BeforeEach
	void init() {
		ReflectionTestUtils.setField(interceptor, "includedUrls", Arrays.asList(
				"/fdx/v6".split(",")));
		ReflectionTestUtils.setField(interceptor, "excludedUrls", Arrays.asList(
				TEST_EXCLUDE_URLS.split(",")));
	}

	@Test
	void doFilterForExcludeUrl() {
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn(TEST_EXCLUDE_URL1);
		assertTrue(interceptor.preHandle(request, response, handler));

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn(TEST_EXCLUDE_URL2);
		assertTrue(interceptor.preHandle(request, response, handler));
	}

	@Test
	void doFilterForValidUrl() throws MalformedURLException, KeySourceException, JSONException {
		List<JWK> jwks = new ArrayList<>();
		JWK dhJwk = JwksUtil.generateRSAJwk("DHJWK-ID", KeyUse.SIGNATURE, JWSAlgorithm.PS256);
		jwks.add(dhJwk);

		JWKSource<SecurityContext> keySet = Mockito.mock(JWKSource.class);
		when(keySet.get(any(), any())).thenReturn(jwks);
		when(remoteJWKSSetHelper.getRemoteJWKSource(anyString(), anyString())).thenReturn(keySet);

		when(appProps.getIssuerUrl()).thenReturn(VALID_DATAHOLDER_ISSUER);
		when(appProps.getAuthServerJwksUriPath()).thenReturn("/jwks");
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + generateSignedDataholderAccessToken(dhJwk, VALID_DATAHOLDER_ISSUER, VALID_TEST_CONSENT_ID, DEFAULT_ACCESS_DURATION));
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn(VALID_URL);
		assertTrue(interceptor.preHandle(request, response, handler));
	}

	@Test
	void doFilterForValidUrl_ExpiredAccessToken() throws KeySourceException, MalformedURLException, JSONException {
		List<JWK> jwks = new ArrayList<>();
		JWK dhJwk = JwksUtil.generateRSAJwk("DHJWK-ID", KeyUse.SIGNATURE, JWSAlgorithm.PS256);
		jwks.add(dhJwk);

		JWKSource<SecurityContext> keySet = Mockito.mock(JWKSource.class);
		when(keySet.get(any(), any())).thenReturn(jwks);
		when(remoteJWKSSetHelper.getRemoteJWKSource(anyString(), anyString())).thenReturn(keySet);

		when(appProps.getAuthServerBaseUrl()).thenReturn(VALID_DATAHOLDER_ISSUER);
		when(appProps.getAuthServerJwksUriPath()).thenReturn("/jwks");
		// Forcing an expired JWT passing in -200
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + generateSignedDataholderAccessToken(dhJwk, VALID_DATAHOLDER_ISSUER, VALID_TEST_CONSENT_ID, -200));
		when(request.getRequestURI()).thenReturn(VALID_URL);
		assertThrows(SecurityException.class, (() -> interceptor.preHandle(request, response, handler)),
				"Request validation failed. Either authorization code is blank or invalid.");
	}

	@Test
	void doFilterForValidUrl_UnexpectedIssuer() throws KeySourceException, MalformedURLException, JSONException {
		List<JWK> jwks = new ArrayList<>();
		JWK dhJwk = JwksUtil.generateRSAJwk("DHJWK-ID", KeyUse.SIGNATURE, JWSAlgorithm.PS256);
		jwks.add(dhJwk);

		JWKSource<SecurityContext> keySet = Mockito.mock(JWKSource.class);
		when(keySet.get(any(), any())).thenReturn(jwks);
		when(remoteJWKSSetHelper.getRemoteJWKSource(anyString(), anyString())).thenReturn(keySet);

		when(appProps.getAuthServerBaseUrl()).thenReturn(VALID_DATAHOLDER_ISSUER);
		when(appProps.getAuthServerJwksUriPath()).thenReturn("/jwks");
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + generateSignedDataholderAccessToken(dhJwk, "INVALID_ISSUER", VALID_TEST_CONSENT_ID, DEFAULT_ACCESS_DURATION));
		when(request.getRequestURI()).thenReturn(VALID_URL);
		assertThrows(SecurityException.class, (() -> interceptor.preHandle(request, response, handler)),
				"Request validation failed. Either authorization code is blank or invalid.");
	}

	@Test
	void doFilterForValidUrl_MissingAuthorizationHeader() {
		List<JWK> jwks = new ArrayList<>();
		JWK dhJwk = JwksUtil.generateRSAJwk("DHJWK-ID", KeyUse.SIGNATURE, JWSAlgorithm.PS256);
		jwks.add(dhJwk);

		when(request.getRequestURI()).thenReturn(VALID_URL);
		assertThrows(SecurityException.class, (() -> interceptor.preHandle(request, response, handler)),
				"Request validation failed. Either authorization code is blank or invalid.");
	}

	@Test
	void doFilterForValidUrl_InvalidAuthorizationHeader_MissingBearer() throws JSONException {
		List<JWK> jwks = new ArrayList<>();
		JWK dhJwk = JwksUtil.generateRSAJwk("DHJWK-ID", KeyUse.SIGNATURE, JWSAlgorithm.PS256);
		jwks.add(dhJwk);
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(generateSignedDataholderAccessToken(dhJwk, "INVALID_ISSUER", VALID_TEST_CONSENT_ID, DEFAULT_ACCESS_DURATION));
		when(request.getRequestURI()).thenReturn(VALID_URL);
		assertThrows(SecurityException.class, (() -> interceptor.preHandle(request, response, handler)),
				"Request validation failed. Either authorization code is blank or invalid.");
	}

	private String generateSignedDataholderAccessToken(JWK dhJwk, String dhIssuer, String consentId, long accessDuration)
			throws JSONException {
		JSONObject authHeaderJson = new JSONObject();
		long iat = Instant.now().getEpochSecond();
		long exp = iat + accessDuration;
		authHeaderJson.put("iat", iat);
		authHeaderJson.put("nbf", iat);
		authHeaderJson.put("exp", exp);
		authHeaderJson.put("iss", dhIssuer);
		authHeaderJson.put("aud", "DUMMY_CLIENT_ID");
		authHeaderJson.put("sub", "DUMMY_CLIENT_ID");
		authHeaderJson.put("consent_id", consentId);
		authHeaderJson.put("sharing_expires_at", exp);
		authHeaderJson.put("scope", FDX_VALID_SCOPES);
		return JwksUtil.sign(dhJwk, authHeaderJson.toString());
	}

}
