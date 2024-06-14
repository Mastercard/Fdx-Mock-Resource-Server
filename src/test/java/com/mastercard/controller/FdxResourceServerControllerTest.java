package com.mastercard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.controller.FdxResourceServerController;
import com.mastercard.fdx.mock.entity.AccountDescriptor;
import com.mastercard.fdx.mock.dto.AccountListResponsePojo;
import com.mastercard.fdx.mock.entity.AccountPaymentNetwork;
import com.mastercard.fdx.mock.dto.AccountPaymentNetworkList;
import com.mastercard.fdx.mock.dto.Accounts;
import com.mastercard.fdx.mock.service.FdxResourceService;
import com.mastercard.fdx.mock.transaction.dto.Transactions;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;

@ExtendWith(MockitoExtension.class)
class FdxResourceServerControllerTest {
	
	@InjectMocks
	FdxResourceServerController fdxResourceServerController; 

	@Mock FdxResourceService fdxResourceService;
	
	
	 private MockMvc mockMvc;
	 
	ObjectMapper objectMapper = new ObjectMapper();
	HttpHeaders headers = new HttpHeaders();
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(fdxResourceServerController).build();
		headers.add("Authorization", "eyJraWQiOiI5OWJjMzNlZS1hMDRkLTRhODktOGFlMC01ZGViZDIxNDVhYWIiLCJ0eXAiOiJhdCtqd3QiLCJhbGciOiJQUzI1NiJ9.eyJzdWIiOiJmZHh1c2VyIiwiYXVkIjoiZGgtZmR4LWNsaWVudC1yZWdpc3RyYXItMiIsIm5iZiI6MTcxNjQ0NjIyNCwiYWNjb3VudF9pZCI6WyIzNzQ2MjcyIiwiMjAwMDEiXSwic2NvcGUiOlsiZmR4OnRyYW5zYWN0aW9uczpyZWFkIiwiZmR4OmFjY291bnRiYXNpYzpyZWFkIiwib3BlbmlkIiwiZmR4OmN1c3RvbWVycGVyc29uYWw6cmVhZCIsImZkeDphY2NvdW50ZGV0YWlsZWQ6cmVhZCIsImZkeDppbnZlc3RtZW50czpyZWFkIiwiZmR4OnBheW1lbnRzdXBwb3J0OnJlYWQiLCJmZHg6YWNjb3VudHBheW1lbnRzOnJlYWQiLCJmZHg6YmlsbHM6cmVhZCIsImZkeDppbWFnZXM6cmVhZCIsImZkeDpyZXdhcmRzOnJlYWQiLCJmZHg6dGF4OnJlYWQiLCJmZHg6c3RhdGVtZW50czpyZWFkIiwiZmR4OmN1c3RvbWVyY29udGFjdDpyZWFkIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTcxNjQ0NjUyNCwiaWF0IjoxNzE2NDQ2MjI0LCJqdGkiOiIyNDRhOGUzMS03OThmLTRlNjYtYjRlNS0zN2YyYTk5NTE5YzQiLCJmZHhDb25zZW50SWQiOiJiNjg5ZjZjZS0wMjIwLTQ2ZjMtYTQ2Ny04N2MwNTgxYjNmNDkiLCJzaGFyaW5nX2V4cGlyZXNfYXQiOjE3NDc5ODIxNDF9.xAWldPhbzOwaJdDefFS8Wr3aeGXstsjmwlGXEJErRUcFdgJxiBvToTtrYUUlV6l5L1L3cr0ENSnIFmwjdp0pQdd1n0XBF39woMMx1_Q5Qbl-N_vf59gTssPRF_8ftqK7YTrhuLEcswrOvlvx0-OvvT_xVrFCwGYwnwu9_9Xazb13sP2CTmVuMuLOn93Sg8zt-lXTAx4iYrMtMf1pTZBRaiGDO7oROJ-1CRtX0_EeNbMNBfeA6-qE1YxnOgUkaY8j2xrJ7FXHNwHpFuA1KQrW38rCSWJGq9l8fl3jt45Z4NIjV-Unm7-QMsqOJ_BxH19f6mZ90aaF_ZJk03o-1CCj7g");
	}
	
	@Test
	void testGetAccountsWithoutAuthentication() throws Exception {
		mockMvc.perform(get("/fdx/v6/accounts")).andExpect(status().is4xxClientError());
	}
	
	@Test
	void testGetAccountsWithAuthentication() throws Exception {
		List<AccountListResponsePojo> accountDescriptors = new ArrayList<>();
		AccountDescriptor accountDescriptor = new AccountDescriptor();
		accountDescriptor.setAccountCategory("LOAN_ACCOUNT");accountDescriptor.setAccountType("LOAN");
		accountDescriptor.setAccountId(20001);accountDescriptor.setNickname("TEST NICKNAME");
		accountDescriptor.setAccountNumber("XXXXX5643");accountDescriptor.setAccountNumberDisplay("5643");
		accountDescriptor.setInstitutionAccountId("20001");accountDescriptor.setProductName("LOAN");
		Accounts accounts = new Accounts();
		accounts.setAccounts(accountDescriptors);
		when(fdxResourceService.getAccountsList(any())).thenReturn(accounts);
		mockMvc.perform(get("/fdx/v6/accounts").headers(headers)).andExpect(status().isOk());
	}
	
	@Test
	void testGetAccountsDetails() throws Exception {
		mockMvc.perform(get("/fdx/v6/accounts/20001").headers(headers)).andExpect(status().isOk());
	}
	
	@Test
	void testGetContactDetails() throws Exception {
		mockMvc.perform(get("/fdx/v6/accounts/20001/contact").headers(headers)).andExpect(status().isOk());
	}
	
	@Test
	void testGetTransactionsDetails() throws Exception {
		List<TransactionsDetails> transactionsDetails = new ArrayList<>();
		TransactionsDetails details = new TransactionsDetails();
		details.setAccountId("20001");
		details.setAmount(10002.00);
		details.setTransactionType("DEBIT");
		transactionsDetails.add(details);
		Transactions transactions = new Transactions();
		transactions.setTransactions(transactionsDetails);
		
		when(fdxResourceService.getTransactions(any())).thenReturn(transactions);
		mockMvc.perform(get("/fdx/v6/accounts/20001/transactions").headers(headers)).andExpect(status().isOk());
	}
	
	@Test
	void testGetPaymentNetworksDetails() throws Exception {
		List<AccountPaymentNetwork>  accountPaymentNetworks= new ArrayList<>();
		AccountPaymentNetwork details = new AccountPaymentNetwork();
		details.setAccountId("20001");
		details.setBankId("23454532");
		details.setType("rtp");
		accountPaymentNetworks.add(details);
		
		AccountPaymentNetworkList accountPaymentNetworkList  = new AccountPaymentNetworkList();
		accountPaymentNetworkList.setPaymentNetworks(accountPaymentNetworks);
		when(fdxResourceService.getPaymentNetworksDetails(any())).thenReturn(accountPaymentNetworkList);
		mockMvc.perform(get("/fdx/v6/accounts/20001/payment-networks").headers(headers)).andExpect(status().isOk());
	}
}
