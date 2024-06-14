package com.mastercard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.config.ApplicationProperties;
import com.mastercard.fdx.mock.controller.AccountConsentController;
import com.mastercard.fdx.mock.dto.AccountConsentResponse;
import com.mastercard.fdx.mock.entity.AccountConsent;
import com.mastercard.fdx.mock.dto.AccountListResponsePojo;
import com.mastercard.fdx.mock.service.AccountConsentService;

@ExtendWith(MockitoExtension.class)
class AccountConsentControllerTest {

	@InjectMocks
	AccountConsentController accountConsentController; 
	
	@Mock
	AccountConsentService accountConsentService;
	
	@Mock
	ApplicationProperties applicationProperties;
	
	private MockMvc mockMvc;
	AccountConsentResponse accountConsentResponse; 
	AccountListResponsePojo accountListResponsePojo;
	ObjectMapper objectMapper = new ObjectMapper();
	HttpHeaders headers = new HttpHeaders();
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(accountConsentController).build();
		accountConsentResponse = new AccountConsentResponse("fdxuser", "testconsentId", null);
		headers.add("Authorization", "test_auth_code");
		accountListResponsePojo = new AccountListResponsePojo("DEPOSIT_ACCOUNT", "testInstitutionAccId", "DEPOSIT", "testNickName", "OPEN", null, "testAccountNumber", "testAccountNumberDisplay", "testProductName", "testDescription", null, null,null,null,null,null);
	}
	
	@Test
	void testGetAccountsByUserId() throws Exception {
		List<AccountListResponsePojo> list = new ArrayList<>();
		list.add(accountListResponsePojo);
		when(accountConsentService.getAccountsByUserId(any())).thenReturn(list);
		mockMvc.perform(get("/consent/accounts?userId=fdxuser").headers(headers)).andExpect(status().isOk());
	}
	

	
	@Test
	void testRegisterConsent() throws Exception {
		AccountConsent consent = new AccountConsent();
		consent.setUserId("fdxuser");
		consent.setAccountIds(Arrays.asList(new String[] {"10001","20001"}));
		consent.setConsentShareDurationSeconds(2000l);
		mockMvc.perform(post("/consent").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(consent)).headers(headers)).andExpect(status().isOk());
	}
	
	
	@Test
	void testUpdateConsent() throws Exception {
		AccountConsent consent = new AccountConsent();
		consent.setUserId("fdxuser");
		consent.setAccountIds(Arrays.asList(new String[] {"10001","20001"}));
		consent.setConsentShareDurationSeconds(2000l);
		mockMvc.perform(put("/consent/1234462").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(consent)).headers(headers)).andExpect(status().isOk());
	}
	
	@Test
	void testGetConsent() throws Exception {
		mockMvc.perform(get("/consent/1234662").headers(headers)).andExpect(status().isOk());
	}
	

}
