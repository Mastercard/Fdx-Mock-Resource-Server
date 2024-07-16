package com.mastercard.fdx.mock.config;

import com.mastercard.fdx.mock.interceptor.AuthorizationInterceptor;
import com.mastercard.fdx.mock.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements  WebMvcConfigurer{

	@Autowired
	private RequestInterceptor requestInterceptor;

	@Autowired
	private AuthorizationInterceptor cdrAuthorizationInterceptor;

	/**
	 * Added 2 interceptors to registry
	 * 1) requestInterceptor, for service-to-service communication that doesn't require OAuth tokens.
	 * 2) cdrAuthorizationInterceptor, for client-to-server communication that requires OAuth tokens.
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor);
		registry.addInterceptor(cdrAuthorizationInterceptor);
	}

}
