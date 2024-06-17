package com.mastercard.fdx.mock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mastercard.fdx.mock.config.ErrorConfig;
import com.mastercard.fdx.mock.dto.AccountListResponsePojo;
import com.mastercard.fdx.mock.dto.AccountPaymentNetworkList;
import com.mastercard.fdx.mock.dto.Accounts;
import com.mastercard.fdx.mock.dto.ErrorPojo;
import com.mastercard.fdx.mock.dto.Page;
import com.mastercard.fdx.mock.dto.StatementsResponse;
import com.mastercard.fdx.mock.entity.AccountContact;
import com.mastercard.fdx.mock.entity.AccountDescriptor;
import com.mastercard.fdx.mock.entity.AccountPaymentNetwork;
import com.mastercard.fdx.mock.entity.Statement;
import com.mastercard.fdx.mock.exception.ApiException;
import com.mastercard.fdx.mock.repository.AccountsRepository;
import com.mastercard.fdx.mock.repository.ContactRepository;
import com.mastercard.fdx.mock.repository.PaymentNetworksRepository;
import com.mastercard.fdx.mock.repository.StatementRepository;
import com.mastercard.fdx.mock.repository.TransactionsRepository;
import com.mastercard.fdx.mock.transaction.dto.Transactions;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;
import com.mastercard.fdx.mock.utilities.JwtDecoderUtils;

@Service
public class FdxResourceService {

	
	 @Autowired
	 AccountsRepository accountsRepository;
	 
	 @Autowired
	 TransactionsRepository transactionsRepository;
	 
	 @Autowired
	 ContactRepository contactRepository;
	 
	 @Autowired
	 PaymentNetworksRepository paymentNetworksRepository;
	 
	 @Autowired ErrorConfig errorConfig;
	 
	 @Autowired StatementRepository statementRepository;
	 
	public Accounts getAccountsList(String authorization) {
		List<? extends AccountDescriptor> accountList = null;
		
		List<String> list = getAccountsIdFromToken(authorization);
		accountList =  accountsRepository.findByAccountIds(list);
		List<AccountListResponsePojo> result = new ArrayList<>();
		if(Objects.nonNull(accountList)) {
			result = accountList.stream()
					.map(s-> new AccountListResponsePojo(s.getAccountCategory(), s.getInstitutionAccountId(), 
					s.getAccountType(), s.getNickname(), s.getStatus(), s.getBalanceAsOf(), s.getAccountNumber(),
					s.getAccountNumberDisplay(), s.getProductName(), s.getDescription(), s.getAccountOpenDate(), s.getAccountCloseDate(),
					s.getCurrentBalance(),s.getOpeningDayBalance(),s.getPrincipalBalance(),s.getAvailableCashBalance())).collect(Collectors.toList());
		}
		 Accounts accounts = new Accounts();
		 accounts.setAccounts(result);
		 return accounts;
	}
	
	public ResponseEntity<AccountDescriptor> getAccountsDetails(String accountId) {
		 AccountDescriptor accountDescriptor = accountsRepository.findByAccountId(accountId);
		if(Objects.isNull(accountDescriptor))
			return new ResponseEntity<>(new AccountDescriptor(errorConfig.getAccountNotFoundErrorCode(), errorConfig.getAccountNotFoundErrorMsg()),HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(accountDescriptor, HttpStatus.OK);
	}
	
	public AccountContact getContactsDetails(String accountId) {
		return contactRepository.findByAccountId(accountId);
	}
	
	public Transactions getTransactions(String accountId) {
		List<TransactionsDetails> transactionsDetails= transactionsRepository.findTransactionsByAccountId(accountId);
		Transactions transactions = new Transactions();
		Page page = new Page();
		page.setTotal(transactionsDetails.size());
		transactions.setPage(page);
		transactions.setTransactions(transactionsDetails);
		return transactions;
	}
	
	public AccountPaymentNetworkList getPaymentNetworksDetails(String accountId) {
		
		List<AccountPaymentNetwork> list = paymentNetworksRepository.findByAccountId(accountId);
		AccountPaymentNetworkList accountPaymentNetworkLists = new AccountPaymentNetworkList();
		Page page = new Page();
		page.setTotal(list.size());
		accountPaymentNetworkLists.setPage(page);
		accountPaymentNetworkLists.setPaymentNetworks(list);
		return accountPaymentNetworkLists;
	}
	
	public void validateTokenWithRequestAccountId(String authorization, String accountId) throws ApiException {
		List<String> list= getAccountsIdFromToken(authorization);
		boolean isAccountIdPresent =  list.contains(accountId);
		if(!isAccountIdPresent) {
			ErrorPojo errorPojo = new ErrorPojo(1003, "Invalid Token");
			throw new ApiException(HttpStatus.BAD_REQUEST,errorPojo, "Invalid Token");
		}
	}

	private List<String> getAccountsIdFromToken(String authorization){
		List<String> list = new ArrayList<String>();
		JSONArray accountIds = JwtDecoderUtils.getAccountIdsFromToken(authorization);
		if(Objects.nonNull(accountIds))
			for (int i = 0; i < accountIds.length() ; i++){
				list.add(accountIds.get(i).toString());
			}
		
		return list;
	}

	public ResponseEntity<StatementsResponse> getStatements(String accountId) {
		StatementsResponse statementsResponse = new StatementsResponse();
		if(Objects.isNull(accountsRepository.findByAccountId(accountId)))
			return new ResponseEntity<>(new StatementsResponse(errorConfig.getAccountNotFoundErrorCode(), errorConfig.getAccountNotFoundErrorMsg()),HttpStatus.BAD_REQUEST);
		
		List<Statement> statement =  statementRepository.findStatementByAccountId(accountId);
		if(Objects.isNull(statement) || CollectionUtils.isEmpty(statement))
			return new ResponseEntity<>(new StatementsResponse(errorConfig.getStatementNotFoundErrorCode(), errorConfig.getStatementNotFoundErrorMsg()),HttpStatus.BAD_REQUEST);
		
		statementsResponse.setStatements(statement);
		Page page = new Page();
		page.setTotal(statement.size());
		statementsResponse.setPage(page);
		return new ResponseEntity<>(statementsResponse,HttpStatus.OK);
	}
	
	public ResponseEntity<?> getStatementsByStatementId(String accountId,String statementId) {
		if(Objects.isNull(accountsRepository.findByAccountId(accountId)))
			return new ResponseEntity<>(new StatementsResponse(errorConfig.getAccountNotFoundErrorCode(), errorConfig.getAccountNotFoundErrorMsg()),HttpStatus.BAD_REQUEST);
		
		Statement statement =  statementRepository.findStatementByStatementId(statementId);
		if(Objects.isNull(statement))
			return new ResponseEntity<>(new StatementsResponse(errorConfig.getStatementNotFoundErrorCode(), errorConfig.getStatementNotFoundErrorMsg()),HttpStatus.BAD_REQUEST);
		
		
		return new ResponseEntity<>(statement.getFile(),HttpStatus.OK);
	}

}
