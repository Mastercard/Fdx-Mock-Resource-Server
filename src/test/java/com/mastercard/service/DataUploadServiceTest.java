package com.mastercard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.dto.DataUploadResponsePojo;
import com.mastercard.fdx.mock.entity.*;
import com.mastercard.fdx.mock.repository.*;
import com.mastercard.fdx.mock.service.DataUploadService;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;
import com.mastercard.fdx.mock.utilities.CommonUtilities;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataUploadServiceTest {

	 @InjectMocks
	 DataUploadService dataUploadService;
	
	 @Mock
	 DepositAccountsRepository depositAccountsRepository;
	 
	 @Mock
	 LoanAccountsRepository loanAccountsRepository; 
	 
	 @Mock
	 InvestmentAccountsRepository investmentAccountsRepository;
	 
	 @Mock
	 LineOfCreditAccountsRepository lineOfCreditAccountsRepository;
	 
	 @Mock
	 AccountConsentRepository accountConsentRepository;
	 
	 @Mock
	 FdxUserRepository fdxUserRepository;
	 
	 @Mock
	 TransactionsRepository transactionsRepository;
	 
	 ObjectMapper mapper = new ObjectMapper();
	 
	 @Test
	 void testAddAccounts() throws JsonMappingException, JsonProcessingException, JSONException {
		 when(fdxUserRepository.findByUserId(any())).thenReturn(new FdxUser(1, "test", "testpwd", "testpwd"));
		 DepositAccount depositAccount = mapper.readValue(CommonUtilities.getFileContent("deposit_acc_details.json"), DepositAccount.class);
		 LoanAccount loanAccounts = mapper.readValue(CommonUtilities.getFileContent("loan_acc_details.json"), LoanAccount.class);
		 InvestmentAccount investmentAccount = mapper.readValue(CommonUtilities.getFileContent("investment_acc_details.json"), InvestmentAccount.class);
		 LineOfCreditAccount lineOfCreditAccount = mapper.readValue(CommonUtilities.getFileContent("lineofcredit_acc_details.json"), LineOfCreditAccount.class);
		 List<Object> accountJsonObject = new ArrayList<>();
		 accountJsonObject.add(depositAccount);accountJsonObject.add(loanAccounts);
		 accountJsonObject.add(investmentAccount);accountJsonObject.add(lineOfCreditAccount);
		 
		 when(depositAccountsRepository.save(any())).thenReturn(depositAccount);
		 when(loanAccountsRepository.save(any())).thenReturn(loanAccounts);
		 when(investmentAccountsRepository.save(any())).thenReturn(investmentAccount);
		 when(lineOfCreditAccountsRepository.save(any())).thenReturn(lineOfCreditAccount);
		 when(accountConsentRepository.save(any())).thenReturn(new AccountConsent());
		 ResponseEntity<DataUploadResponsePojo> response = dataUploadService.addAccount("test", accountJsonObject);
		 assertNotNull(response);
		 assertTrue( response.getStatusCode().is2xxSuccessful());
	 }
	 
	 @Test
	 void testAddAccountsInvalidUserId() throws JsonMappingException, JsonProcessingException, JSONException {
		 when(fdxUserRepository.findByUserId(any())).thenReturn(null);
		 DepositAccount depositAccount = mapper.readValue(CommonUtilities.getFileContent("deposit_acc_details.json"), DepositAccount.class);
		 List<Object> accountJsonObject = new ArrayList<>();
		 accountJsonObject.add(depositAccount);
		 ResponseEntity<DataUploadResponsePojo> response = dataUploadService.addAccount("test", accountJsonObject);
		 assertNotNull(response);
		 assertEquals(1001, response.getBody().getErrorCode());
	 }
	 
	 @Test
	 void testAddAccountsInvalidAccountCategoryId() throws JsonMappingException, JsonProcessingException, JSONException {
		 when(fdxUserRepository.findByUserId(any())).thenReturn(new FdxUser(1, "test", "testpwd", "testpwd"));
		 List<Object> accountJsonObject = new ArrayList<>();
		 DepositAccount depositAccount = new DepositAccount();
		 depositAccount.setAccountCategory("XXXXXX");
		 accountJsonObject.add(depositAccount);
		 ResponseEntity<DataUploadResponsePojo> response = dataUploadService.addAccount("test", accountJsonObject);
		 assertNotNull(response);
		 assertEquals(1002, response.getBody().getErrorCode());
	 }
	 
	 @Test
	 void testAddTransactions() {
		 List<TransactionsDetails> transactionsDetails = new ArrayList<>();
		 TransactionsDetails details = new TransactionsDetails();
		 details.setTransactionId("1234");details.setAccountCategory("DEPOSIT_ACCOUNT");
		 details.setAmount(2440.00);details.setAccountId("testAccId");
		 
		 when(transactionsRepository.saveAll(any())).thenReturn(transactionsDetails);
		 ResponseEntity<DataUploadResponsePojo> response = dataUploadService.addTransactions(transactionsDetails);
		 assertNotNull(response);
		 assertTrue( response.getStatusCode().is2xxSuccessful());
	 }
	 
	 @Test
	 void testAddAccountError() throws JsonMappingException, JsonProcessingException, JSONException {
		 when(fdxUserRepository.findByUserId(any())).thenReturn(new FdxUser(1, "test", "testpwd", "testpwd"));
		 DepositAccount depositAccount = mapper.readValue(CommonUtilities.getFileContent("deposit_acc_details.json"), DepositAccount.class);
		 when(depositAccountsRepository.save(any())).thenThrow(NullPointerException.class);
		 List<Object> accountJsonObject = new ArrayList<>();
		 accountJsonObject.add(depositAccount);
		 ResponseEntity<DataUploadResponsePojo> response = dataUploadService.addAccount("test", accountJsonObject);
		 assertNotNull(response);
		 assertEquals(1004, response.getBody().getErrorCode());
	 }
	 
}
