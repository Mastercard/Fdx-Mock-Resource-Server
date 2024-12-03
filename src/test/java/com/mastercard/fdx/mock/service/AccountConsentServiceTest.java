package com.mastercard.fdx.mock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mastercard.fdx.mock.dto.AccountConsentRequestDTO;
import com.mastercard.fdx.mock.dto.AccountConsentResponse;
import com.mastercard.fdx.mock.dto.AccountListResponsePojo;
import com.mastercard.fdx.mock.entity.AccountConsent;
import com.mastercard.fdx.mock.entity.AccountDescriptor;
import com.mastercard.fdx.mock.repository.AccountConsentRepository;
import com.mastercard.fdx.mock.repository.AccountsRepository;

@ExtendWith(MockitoExtension.class)
class AccountConsentServiceTest {
	
	@InjectMocks
	AccountConsentService accountConsentService;

	@Mock
	AccountConsentRepository accountConsentRepository;
	
	@Mock
	AccountsRepository accountsRepository;
	
	AccountConsent accountConsent;
	
	AccountConsentRequestDTO accountConsentRequestDTO;
	
	@BeforeEach
	void setUp() {
		accountConsent = new AccountConsent();
		accountConsentRequestDTO = new AccountConsentRequestDTO();
		accountConsent.setAccountIds(Arrays.asList("10001","20001"));
		accountConsent.setAllAccountIds(Arrays.asList("10001","20001"));
		accountConsent.setConsentId("testConsentId");
		accountConsent.setConsentShareDurationSeconds(30000l);
		accountConsent.setUserId("fdxuser");
	}
	
	@Test
	void testGetAccountsByUserId() {
		
		List<AccountDescriptor> accountDescriptors = new ArrayList<>();
		AccountDescriptor accountDescriptor = new AccountDescriptor();
		accountDescriptor.setAccountCategory("LOAN_ACCOUNT");accountDescriptor.setAccountType("LOAN");
		accountDescriptor.setAccountId(20001);accountDescriptor.setNickname("TEST NICKNAME");
		accountDescriptor.setAccountNumber("XXXXX5643");accountDescriptor.setAccountNumberDisplay("5643");
		accountDescriptor.setInstitutionAccountId("20001");accountDescriptor.setProductName("LOAN");
		accountDescriptors.add(accountDescriptor);
		when(accountsRepository.findByAccountIds(any())).thenReturn(accountDescriptors);
		when(accountConsentRepository.findAccountsByUserId(any())).thenReturn(accountConsent);
		
		List<AccountListResponsePojo> response =  accountConsentService.getAccountsByUserId("fdxuser");
		assertNotNull(response);
		assertEquals(1, response.size());
	}

	@Test
	void testGetAccountsByUserIdEmpty() {
		when(accountConsentRepository.findAccountsByUserId(any())).thenReturn(null);

		List<AccountListResponsePojo> response =  accountConsentService.getAccountsByUserId("fdxuser");
		assertTrue(response.isEmpty());
	}

	
	@Test
	void testSaveConsentAccount() {
		when(accountConsentRepository.findAccountsByUserId(any())).thenReturn(accountConsent);
		when(accountConsentRepository.save(any())).thenReturn(accountConsent);
		
		AccountConsentResponse response =  accountConsentService.saveConsentAccount(accountConsentRequestDTO);
		assertNotNull(response);
		assertEquals(accountConsent.getConsentId(), response.getConsentId());
	}
	
	@Test
	void testUpdateConsentAccount() {
		when(accountConsentRepository.findAccountsByConsentId(any())).thenReturn(Optional.ofNullable(accountConsent));
		when(accountConsentRepository.save(any())).thenReturn(accountConsent);
		
		AccountConsentResponse response =  accountConsentService.updateConsentAccount(accountConsentRequestDTO,"testConsentId");
		assertNotNull(response);
		assertEquals(accountConsent.getConsentId(), response.getConsentId());
	}

	@Test
	void testUpdateConsentAccountEmpty() {
		when(accountConsentRepository.findAccountsByConsentId(any())).thenReturn(Optional.ofNullable(null));
		AccountConsentResponse response =  accountConsentService.updateConsentAccount(accountConsentRequestDTO,"testConsentId");
		assertNull(response);
	}


	@Test
	void testGetConsent() {
		when(accountConsentRepository.findAccountsByConsentId(any())).thenReturn(Optional.ofNullable(accountConsent));
		AccountConsent response =	accountConsentService.getConsent("testConsentId");
		assertNotNull(response);
		assertEquals(accountConsent.getConsentId(), response.getConsentId());
	}
	
	@Test
	void testGetConsentError() {
		when(accountConsentRepository.findAccountsByConsentId(any())).thenReturn(null);
		AccountConsent response =	accountConsentService.getConsent("testConsentId");
		assertNotNull(response);
		assertEquals(2001, response.getErrorCode());
		assertEquals("No Consent Found for testConsentId consentId", response.getErrorMessage());
	}
}
