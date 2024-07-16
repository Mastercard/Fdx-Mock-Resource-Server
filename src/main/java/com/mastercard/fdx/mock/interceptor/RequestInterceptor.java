package com.mastercard.fdx.mock.interceptor;

import com.mastercard.fdx.mock.config.ApplicationProperties;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

	@Value("#{'${intercept.int.res.server.included.urls}'.split(',')}")
	private List<String> includedUrls;

	@Value("#{'${intercept.int.res.server.excluded.urls}'.split(',')}")
	private List<String> excludedUrls;

	private Set<String> listOfUniqueTokenSet;

	@Autowired
	private SecurityInterceptConfig securityInterceptConfig;

	@Autowired
	ApplicationProperties applicationProperties;

	@PostConstruct
	public void initValidTokens() {
		listOfUniqueTokenSet = new HashSet<>();
		listOfUniqueTokenSet.add(applicationProperties.getResourceServerAuthCode());
	}

	/**
	 * Below method will intercept the request and validate whether for this API call,
	 * validation needs to be performed with internal tokens or allow the request to continue processing.
	 * @param req
	 * @param response
	 * @param handler
	 * @return
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) {
		log.debug("Incoming Request:- {} {} ", req.getMethod(), req.getRequestURI());
		if (!ignoreRequest(req.getRequestURI(), req.getMethod())) {
			authenticateRequest(req);
			authorizeRequest(req);
		}
		log.debug("Request allowed for Processing:- {} {} ", req.getMethod(), req.getRequestURI());
		return true;
	}

	private boolean ignoreRequest(String url, String requestMethod) {
		if (url.equals("/"))
			return true;
		for (String includedUrl : includedUrls) {
			if (url.startsWith(includedUrl))
				return false;
		}
		for (String excludeUrl : excludedUrls) {
			if (org.springframework.util.StringUtils.hasText(excludeUrl) && url.startsWith(excludeUrl))
				return true;
		}
		return RequestMethod.OPTIONS.name().equals(requestMethod);
	}

	private void authorizeRequest(HttpServletRequest req) {

		RequestMethodUri reqMethodUri = new RequestMethodUri(req.getMethod(), req.getRequestURI());
		String requiredToken = securityInterceptConfig.getToken(reqMethodUri);
		if (requiredToken == null) {
			throwSecurityException("Invalid Url");
		}
		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (!requiredToken.equals(token)) {
			throwSecurityException("Token not authorized for this url");
		}

	}

	private void authenticateRequest(HttpServletRequest req) {
		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		if ((StringUtils.isBlank(token) || !listOfUniqueTokenSet.contains(token))) {
			throwSecurityException(
					"Request validation failed. Either authorization code is blank or invalid.");
		}
	}

	private void throwSecurityException(String errMsg) {
		log.error(errMsg);
		throw new SecurityException(errMsg);
	}
}
