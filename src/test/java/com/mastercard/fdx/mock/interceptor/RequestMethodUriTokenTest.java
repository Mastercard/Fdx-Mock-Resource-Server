package com.mastercard.fdx.mock.interceptor;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

class RequestMethodUriTokenTest {

	@Test
	void testRequestMethodUriToken() {
		assertThat(RequestMethodUriToken.class, allOf(hasValidGettersAndSetters()));
	}

}
