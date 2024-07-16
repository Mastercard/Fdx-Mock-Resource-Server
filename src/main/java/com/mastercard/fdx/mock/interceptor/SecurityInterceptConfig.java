package com.mastercard.fdx.mock.interceptor;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "security-interceptor")
@PropertySource(value = "classpath:security-interceptor.properties")
public class SecurityInterceptConfig {

	@Getter
	@Setter
	private List<RequestMethodUriToken> requestMethodUriTokens;
	private Map<RequestMethodUri, String> requestMethodUriTokenMapping;

	/**
	 * Below method will config the security interceptor for Authorization header validations.
	 * Configures HTTP METHOD & ENDPOINT that requires internal security token stored in secret service or environment variable.
	 */
	@PostConstruct
	public void initMapping() {
		if (Objects.nonNull(requestMethodUriTokens)) {
			log.debug("Constructing request method uri token mapping...");
			requestMethodUriTokenMapping = new HashMap<>();
			requestMethodUriTokens.forEach(r -> requestMethodUriTokenMapping.put(r.getRequestMethodUri(), r.getTokenName()));
			log.debug("Constructed request method uri token mapping:[{}]", requestMethodUriTokenMapping);
		}
	}

	public String getToken(RequestMethodUri requestMethodUri) {
		String token = null;
		if (Objects.nonNull(requestMethodUriTokenMapping)) {
			log.debug("Finding token for requestMethodUri:[{}]...", requestMethodUri);
			token = requestMethodUriTokenMapping.get(requestMethodUri);
			log.debug("Found token for requestMethodUri:[{}], token:[{}]...", requestMethodUri, token);
		}
		return token;
	}
}
