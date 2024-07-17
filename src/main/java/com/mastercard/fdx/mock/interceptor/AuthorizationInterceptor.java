package com.mastercard.fdx.mock.interceptor;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.mastercard.fdx.mock.utilities.RemoteJWKSSetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mastercard.fdx.mock.config.ApplicationProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor{

	  @Autowired
	  ApplicationProperties appProps;

	    @Autowired
	    private RemoteJWKSSetHelper remoteJWKSSetHelper;
	    

	    @Value("#{'${intercept.res.server.included.urls}'.split(',')}")
	    private List<String> includedUrls;

	    @Value("#{'${intercept.res.server.excluded.urls}'.split(',')}")
	    private List<String> excludedUrls;

		/**
		 * Below method will intercept the request and validate whether for this API call,
		 * validation needs to be performed based on OAuth token received.
		 * @param req
		 * @param response
		 * @param handler
		 * @return
		 */
		@Override
	    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) {
	        if (!ignoreRequest(req.getRequestURI())) {
	            validateAuthorization(req);
	        }
	        log.debug("Request allowed for Processing:- {} {} ", req.getMethod(), req.getRequestURI());
	        return true;
	    }

	    private void validateAuthorization(HttpServletRequest req) {
	            try {
	                String authorisation = req.getHeader(HttpHeaders.AUTHORIZATION);
	                BearerAccessToken authToken = BearerAccessToken.parse(authorisation);

	                JWTClaimsSetVerifier<SecurityContext> claimsVerifier = new DefaultJWTClaimsVerifier<>(
	                        new JWTClaimsSet.Builder()
	                                .issuer(appProps.getIssuerUrl())
	                                .build(),
	                        // names of required claims
	                        new HashSet<>(Collections.emptyList())
	                );
					String jwksUrl = appProps.getAuthServerBaseUrl() + appProps.getAuthServerJwksUriPath();
	                JWKSource<SecurityContext> keySource = remoteJWKSSetHelper.getRemoteJWKSource(jwksUrl, "");
	                JWSVerificationKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.PS256, keySource);

	                DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
	                jwtProcessor.setJWSTypeVerifier(new DefaultJOSEObjectTypeVerifier<>(JOSEObjectType.JWT, new JOSEObjectType("at+jwt")));
	                jwtProcessor.setJWTClaimsSetVerifier(claimsVerifier);
	                jwtProcessor.setJWSKeySelector(keySelector);
	                jwtProcessor.process(authToken.getValue(), null);
	                
	            } catch (com.nimbusds.oauth2.sdk.ParseException | MalformedURLException | BadJOSEException | ParseException
	                     | JOSEException ex) {
	                log.error("FI-Tracker-id/x-fapi-interaction-id: {} , Request failed while validating authorization", "");
	                throwSecurityException(
	                        "Request validation failed. Either authorization code is blank or invalid.", ex);
	            }
	        
	    }

	    private boolean ignoreRequest(String url) {
	        if (!isIncluded(url))
	            return true;

	        for (String excludeUrl : excludedUrls) {//NOSONAR
	            if (StringUtils.hasText(excludeUrl) && url.startsWith(excludeUrl))
	                return true;
	        }
	        return false;
	    }

	    private boolean isIncluded(String url) {
	        for (String includeUrl : includedUrls) {//NOSONAR
	            if (StringUtils.hasText(includeUrl) && url.startsWith(includeUrl))
	                return true;
	        }
	        return false;
	    }

	    public static void throwSecurityException(String errMsg, Throwable ex) {
	        log.error(errMsg, ex);
	        throw new SecurityException(errMsg);
	    }

}
