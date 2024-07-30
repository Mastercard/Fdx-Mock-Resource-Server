package com.mastercard.fdx.mock.interceptor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class RequestMethodUriTest {
	
	@InjectMocks
	private RequestMethodUri firstRequestMethodUri;
	
	@BeforeEach
	public void setup() {
		firstRequestMethodUri = new RequestMethodUri();
		firstRequestMethodUri.setMethod("GET");
		firstRequestMethodUri.setUri("/testURI");
	}

	@Test
	void testRequestMethodUriToken() {
		assertThat(RequestMethodUri.class, allOf(hasValidGettersAndSetters()));
	}

	@Test
	void testPositive() {
		RequestMethodUri secondRequestMethodUri = new RequestMethodUri("GET", "/testURI");		
		Assertions.assertEquals(firstRequestMethodUri, secondRequestMethodUri);
	}
	
	@Test
	void testNegative() {
		RequestMethodUri secondRequestMethodUri = new RequestMethodUri("GET", "/testURISample/");		
		Assertions.assertNotEquals(firstRequestMethodUri, secondRequestMethodUri);
	}
	
	@Test
	void testHashCode() {
		RequestMethodUri secondRequestMethodUri = new RequestMethodUri("GET", "/testURISample/");		
		Assertions.assertNotEquals(firstRequestMethodUri, secondRequestMethodUri);
		
		int oneCode = firstRequestMethodUri.hashCode();
		Assertions.assertEquals(oneCode, secondRequestMethodUri.hashCode());
	}


}
