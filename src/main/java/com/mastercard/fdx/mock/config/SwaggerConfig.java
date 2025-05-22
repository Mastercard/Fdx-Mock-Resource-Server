package com.mastercard.fdx.mock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {


	private static final String API_KEY = "apiKey";

	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI().addServersItem(new Server().url("/"))
			.info(new Info().title("Welcome to Fdx Mock Resource Server API!")
				.description("Fdx Mock Resource Server API reference for developers")
				.version("1.0.0")
				.termsOfService("https://mastercard.com")
				.license(new License().name("Mastercard Product").url("https://mastercard.com")))
				.addSecurityItem(new SecurityRequirement().addList(API_KEY))
				.components(new Components().addSecuritySchemes(API_KEY, apiKeySecuritySchema()));
	}

	private SecurityScheme apiKeySecuritySchema() {
		return new SecurityScheme().name(HttpHeaders.AUTHORIZATION)
				.description("Authorization Token to access Secured API!").in(SecurityScheme.In.HEADER)
				.type(SecurityScheme.Type.APIKEY);
	}

}
