package com.mastercard.fdx.mock.controller;

import com.mastercard.fdx.mock.controller.ApplicationCheckController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ApplicationCheckControllerTest {

	@InjectMocks
	private ApplicationCheckController appCheckController;

	@Test
	void testGetHealth() {
		assertEquals("Fdx mock Resource Server is up and running.", appCheckController.getHealth().getBody());
		assertEquals(200, appCheckController.getHealth().getStatusCodeValue());
	}

	@Test
	void testStartupStatus() {
		assertEquals("Fdx mock Resource Server dependent services are up and running.", appCheckController.getStartupStatus().getBody());
		assertEquals(200, appCheckController.getHealth().getStatusCodeValue());
	}

	@Test
	void testShutdown() {
		assertEquals("{\"message\":\"SUCCESS\"}", appCheckController.shutdown().getBody());
		assertEquals(200, appCheckController.shutdown().getStatusCodeValue());
	}

	@Test
	void testLivenessProbe() {
		assertEquals(200, appCheckController.validateLiveness().getStatusCodeValue());
	}

}
