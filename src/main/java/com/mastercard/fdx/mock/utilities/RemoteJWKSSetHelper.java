package com.mastercard.fdx.mock.utilities;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This Helper class allows us to autowired into class that need to obtain a RemoteJWKSet, and during Unit testing
 * can be mocked to return the desired list of JWK's
 */

@Component
@Slf4j
public class RemoteJWKSSetHelper {

	public JWKSource<SecurityContext> getRemoteJWKSource(String url, String finRequestId) throws MalformedURLException {
		log.info("FI-Tracker-id/x-fapi-interaction-id: {} inside getRemoteJWKSource", finRequestId);
		int connectTimeoutInMs = 5000;
		int readTimeoutInMs = 5000;
		return JWKSourceBuilder.create(new URL(url), new DefaultResourceRetriever(connectTimeoutInMs, readTimeoutInMs))
				.retrying(true)
				.cache(true).build();
	}

}
