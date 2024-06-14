package com.mastercard.fdx.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.TimeZone;

@SpringBootApplication
@Slf4j
@EnableWebMvc
public class MockResourceServerApplication {

	
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(MockResourceServerApplication.class, args);
		log.info("!---Mock Resource Server Application Started Successfully----!");
	}
}
