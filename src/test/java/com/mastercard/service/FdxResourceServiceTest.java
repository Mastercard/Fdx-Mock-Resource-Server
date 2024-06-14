package com.mastercard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mastercard.fdx.mock.repository.AccountsRepository;
import com.mastercard.fdx.mock.repository.ContactRepository;
import com.mastercard.fdx.mock.repository.PaymentNetworksRepository;
import com.mastercard.fdx.mock.repository.TransactionsRepository;
import com.mastercard.fdx.mock.entity.AccountContact;
import com.mastercard.fdx.mock.entity.AccountDescriptor;
import com.mastercard.fdx.mock.entity.AccountPaymentNetwork;
import com.mastercard.fdx.mock.dto.AccountPaymentNetworkList;
import com.mastercard.fdx.mock.dto.Accounts;
import com.mastercard.fdx.mock.service.FdxResourceService;
import com.mastercard.fdx.mock.transaction.dto.Transactions;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;

@ExtendWith(MockitoExtension.class)
class FdxResourceServiceTest {

	
	@InjectMocks
	FdxResourceService fdxResourceService;
	
	 @Mock
	 AccountsRepository accountsRepository;
	 
	 @Mock
	 TransactionsRepository transactionsRepository;
	 
	 @Mock
	 ContactRepository contactRepository;
	 
	 @Mock
	 PaymentNetworksRepository paymentNetworksRepository;
	 
	 String authrorization = "eyJraWQiOiI5OWJjMzNlZS1hMDRkLTRhODktOGFlMC01ZGViZDIxNDVhYWIiLCJ0eXAiOiJhdCtqd3QiLCJhbGciOiJQUzI1NiJ9.eyJzdWIiOiJmZHh1c2VyIiwiYXVkIjoiZGgtZmR4LWNsaWVudC1yZWdpc3RyYXItMiIsIm5iZiI6MTcxNjQ0NjIyNCwiYWNjb3VudF9pZCI6WyIzNzQ2MjcyIiwiMjAwMDEiXSwic2NvcGUiOlsiZmR4OnRyYW5zYWN0aW9uczpyZWFkIiwiZmR4OmFjY291bnRiYXNpYzpyZWFkIiwib3BlbmlkIiwiZmR4OmN1c3RvbWVycGVyc29uYWw6cmVhZCIsImZkeDphY2NvdW50ZGV0YWlsZWQ6cmVhZCIsImZkeDppbnZlc3RtZW50czpyZWFkIiwiZmR4OnBheW1lbnRzdXBwb3J0OnJlYWQiLCJmZHg6YWNjb3VudHBheW1lbnRzOnJlYWQiLCJmZHg6YmlsbHM6cmVhZCIsImZkeDppbWFnZXM6cmVhZCIsImZkeDpyZXdhcmRzOnJlYWQiLCJmZHg6dGF4OnJlYWQiLCJmZHg6c3RhdGVtZW50czpyZWFkIiwiZmR4OmN1c3RvbWVyY29udGFjdDpyZWFkIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTcxNjQ0NjUyNCwiaWF0IjoxNzE2NDQ2MjI0LCJqdGkiOiIyNDRhOGUzMS03OThmLTRlNjYtYjRlNS0zN2YyYTk5NTE5YzQiLCJmZHhDb25zZW50SWQiOiJiNjg5ZjZjZS0wMjIwLTQ2ZjMtYTQ2Ny04N2MwNTgxYjNmNDkiLCJzaGFyaW5nX2V4cGlyZXNfYXQiOjE3NDc5ODIxNDF9.xAWldPhbzOwaJdDefFS8Wr3aeGXstsjmwlGXEJErRUcFdgJxiBvToTtrYUUlV6l5L1L3cr0ENSnIFmwjdp0pQdd1n0XBF39woMMx1_Q5Qbl-N_vf59gTssPRF_8ftqK7YTrhuLEcswrOvlvx0-OvvT_xVrFCwGYwnwu9_9Xazb13sP2CTmVuMuLOn93Sg8zt-lXTAx4iYrMtMf1pTZBRaiGDO7oROJ-1CRtX0_EeNbMNBfeA6-qE1YxnOgUkaY8j2xrJ7FXHNwHpFuA1KQrW38rCSWJGq9l8fl3jt45Z4NIjV-Unm7-QMsqOJ_BxH19f6mZ90aaF_ZJk03o-1CCj7g";
	 
	 
	 @Test
	 void testGetAccountsList() {
		 AccountDescriptor accountDescriptor = new AccountDescriptor();
			accountDescriptor.setAccountCategory("LOAN_ACCOUNT");accountDescriptor.setAccountType("LOAN");
			accountDescriptor.setAccountId(20001);accountDescriptor.setNickname("TEST NICKNAME");
			accountDescriptor.setAccountNumber("XXXXX5643");accountDescriptor.setAccountNumberDisplay("5643");
			accountDescriptor.setInstitutionAccountId("20001");accountDescriptor.setProductName("LOAN");
			List<AccountDescriptor> list = new ArrayList<>();
			list.add(accountDescriptor);
		 when(accountsRepository.findByAccountIds(any())).thenReturn(list);
		 
		 Accounts response = fdxResourceService.getAccountsList(authrorization);
		 assertNotNull(response);
	 }
	 
	 @Test
	 void testGetAccountsDetails() {
		 	AccountDescriptor accountDescriptor = new AccountDescriptor();
			accountDescriptor.setAccountCategory("LOAN_ACCOUNT");accountDescriptor.setAccountType("LOAN");
			accountDescriptor.setAccountId(20001);accountDescriptor.setNickname("TEST NICKNAME");
			accountDescriptor.setAccountNumber("XXXXX5643");accountDescriptor.setAccountNumberDisplay("5643");
			accountDescriptor.setInstitutionAccountId("20001");accountDescriptor.setProductName("LOAN");
			
			 when(accountsRepository.findByAccountId(any())).thenReturn(accountDescriptor);
			 
			 AccountDescriptor response = fdxResourceService.getAccountsDetails("test");
			 assertNotNull(response);
	 }
	 
	 @Test
	 void testGetContactDetails() {
		 AccountContact accountContact = new AccountContact();
		 accountContact.setAccountId("testAccountId");
		 
		 when(contactRepository.findByAccountId(any())).thenReturn(accountContact);
		 AccountContact response = fdxResourceService.getContactsDetails("testAccountId");
		 assertNotNull(response);
	 }
	 
	 @Test
	 void testGetTransactiosDetails() {
		 List<TransactionsDetails> transactionsDetails = new ArrayList<>();
		 TransactionsDetails details = new TransactionsDetails();
		 details.setTransactionId("124555");
		 transactionsDetails.add(details);
		 when(transactionsRepository.findTransactionsByAccountId(any())).thenReturn(transactionsDetails);
		 Transactions response = fdxResourceService.getTransactions("testAccountId");
		 assertNotNull(response);
		 assertEquals(1,response.getTransactions().size());
		 assertEquals("124555",response.getTransactions().get(0).getTransactionId());
	 }
	 
	 @Test
	 void testGetPaymentNetworksDetails() {
		 List<AccountPaymentNetwork> accountPaymentNetworks = new ArrayList<>();
		 AccountPaymentNetwork details = new AccountPaymentNetwork();
		 details.setBankId("1234");
		 accountPaymentNetworks.add(details);
		 when(paymentNetworksRepository.findByAccountId(any())).thenReturn(accountPaymentNetworks);
		 AccountPaymentNetworkList response = fdxResourceService.getPaymentNetworksDetails("testAccountId");
		 assertNotNull(response);
		 assertEquals(1,response.getPaymentNetworks().size());
		 assertEquals("1234",response.getPaymentNetworks().get(0).getBankId());
	 }
}
