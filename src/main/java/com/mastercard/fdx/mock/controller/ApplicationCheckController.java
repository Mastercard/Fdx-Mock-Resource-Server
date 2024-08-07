package com.mastercard.fdx.mock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ApplicationCheckController {

	private static final String HEALTH_API_SUCCESS_MESSAGE = "Fdx mock Resource Server is up and running.";

	@GetMapping(path = "/health")
	public ResponseEntity<String> getHealth() {
		log.debug("getHealth api called...");
		return new ResponseEntity<>(HEALTH_API_SUCCESS_MESSAGE, HttpStatus.OK);
	}

	@GetMapping(path = "/startupStatus")
	public ResponseEntity<String> getStartupStatus() {
		log.debug("startupStatus api called...");
		return new ResponseEntity<>("Fdx mock Resource Server dependent services are up and running.", HttpStatus.OK);
	}

	@GetMapping(path = "/livenessProbe")
	public ResponseEntity<String> validateLiveness() {
		log.debug("livenessProbe api called...");
		return new ResponseEntity<>(HEALTH_API_SUCCESS_MESSAGE, HttpStatus.OK);
	}

	@GetMapping(value = "/shutdown")
	public ResponseEntity<String> shutdown() {
		log.info("Graceful shutdown called");
		return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"SUCCESS\"}");
	}
}
