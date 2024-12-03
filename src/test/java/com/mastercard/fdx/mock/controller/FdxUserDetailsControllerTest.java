package com.mastercard.fdx.mock.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.dto.FdxUserResponseDTO;
import com.mastercard.fdx.mock.entity.FdxUser;
import com.mastercard.fdx.mock.service.FdxUserService;

@ExtendWith(MockitoExtension.class)
class FdxUserDetailsControllerTest {

	@InjectMocks
	FdxUserDetailsController fdxUserDetailsController;
	
	@Mock
	FdxUserService fdxUserService;
	
	
	private MockMvc mockMvc;
	
	HttpHeaders headers = new HttpHeaders();
	ObjectMapper objectMapper = new ObjectMapper();
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(fdxUserDetailsController).build();
		headers.add("Authorization", "test_auth_code");
	}
	
	@Test
	void testGetUserByUserId() throws Exception {
		when(fdxUserService.getUser(any())).thenReturn(new ResponseEntity<FdxUserResponseDTO> (new FdxUserResponseDTO("fdxuser", "dfdff325dvssfasfs"),HttpStatus.OK));
		mockMvc.perform(get("/user/fdxuser").headers(headers)).andExpect(status().isOk());
	}
	
	@Test
	void testGetUserByUserIdNoUserFound() throws Exception {
		when(fdxUserService.getUser(any())).thenReturn(new ResponseEntity<FdxUserResponseDTO> (new FdxUserResponseDTO(),HttpStatus.BAD_REQUEST));
		mockMvc.perform(get("/user/fdxuser").headers(headers)).andExpect(status().isBadRequest());
	}
	
	@Test
	void testSaveUser() throws Exception {
		FdxUser fdxuser =  new FdxUser(1, "fdxuser", null, "test");
		when(fdxUserService.saveUser(any())).thenReturn((new ResponseEntity<FdxUserResponseDTO> (new FdxUserResponseDTO("fdxuser", "dfdff325dvssfasfs"),HttpStatus.OK)));
		mockMvc.perform(post("/user").headers(headers).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fdxuser))).andExpect(status().isOk());
	}
	
	@Test
	void testSaveUserAlredayExist() throws Exception {
		FdxUser fdxuser =  new FdxUser(1, "fdxuser", null, "test");
		when(fdxUserService.saveUser(any())).thenReturn((new ResponseEntity<FdxUserResponseDTO> (new FdxUserResponseDTO( "fdxuser", "dfdff325dvssfasfs"),HttpStatus.CONFLICT)));
		mockMvc.perform(post("/user").headers(headers).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fdxuser))).andExpect(status().isConflict());
	}
}
