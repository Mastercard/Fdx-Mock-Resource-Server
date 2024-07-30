package com.mastercard.fdx.mock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mastercard.fdx.mock.repository.FdxUserRepository;
import com.mastercard.fdx.mock.entity.FdxUser;
import com.mastercard.fdx.mock.service.FdxUserService;

@ExtendWith(MockitoExtension.class)
class FdxUserServiceTest {

	@InjectMocks FdxUserService fdxUserService;
	
	@Mock FdxUserRepository fdxUserRepository;
	
	@Test
	void testGetUser() {
		when(fdxUserRepository.findByUserId(any())).thenReturn(new FdxUser(1, "test", "testpwd", null));
		ResponseEntity<FdxUser> response = fdxUserService.getUser("test");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("test", response.getBody().getUserId());
	}
	
	@Test
	void testGetUserNoUserFound() {
		when(fdxUserRepository.findByUserId(any())).thenReturn(null);
		ResponseEntity<FdxUser> response = fdxUserService.getUser("test");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void testSaveUser() {
		when(fdxUserRepository.findByUserId(any())).thenReturn(null);
		when(fdxUserRepository.save(any())).thenReturn(new FdxUser(2, "test1", "testpwd1", "testpwd1"));
		ResponseEntity<FdxUser> response = fdxUserService.saveUser(new FdxUser(2, "test1", "testpwd1", "testpwd1"));
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("test1", response.getBody().getUserId());
	}
	
	@Test
	void testSaveUserError() {
		when(fdxUserRepository.findByUserId(any())).thenReturn(new FdxUser(1, "test", "testpwd", null));
		ResponseEntity<FdxUser> response = fdxUserService.saveUser(new FdxUser(1, "test", "testpwd", null));
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}
}
