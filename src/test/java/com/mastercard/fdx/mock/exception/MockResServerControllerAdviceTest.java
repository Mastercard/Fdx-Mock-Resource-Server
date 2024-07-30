package com.mastercard.fdx.mock.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MockResServerControllerAdviceTest {

	@InjectMocks
	private MockResServerControllerAdvice mockResServerControllerAdvice;

	@Test
	void testHandleUnknownExceptions(){
		ResponseEntity<ErrorResponse> res = mockResServerControllerAdvice.handleUnknownExceptions(null, new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		assertNotNull(res);
		assertEquals(500, res.getStatusCode().value());
	}

	@Test
	void testHandleSecurityException(){
		ResponseEntity<ErrorResponse> res = mockResServerControllerAdvice.handleSecurityExceptions(null, new SecurityException(""));
		assertNotNull(res);
		assertEquals(401, res.getStatusCode().value());
	}

}
