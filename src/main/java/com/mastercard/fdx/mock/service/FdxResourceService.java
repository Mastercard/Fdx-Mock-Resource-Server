package com.mastercard.fdx.mock.service;

import com.mastercard.fdx.mock.dto.*;
import com.mastercard.fdx.mock.entity.*;
import com.mastercard.fdx.mock.exception.ApiException;
import com.mastercard.fdx.mock.repository.AccountsRepository;
import com.mastercard.fdx.mock.repository.ContactRepository;
import com.mastercard.fdx.mock.repository.PaymentNetworksRepository;
import com.mastercard.fdx.mock.repository.TransactionsRepository;
import com.mastercard.fdx.mock.transaction.dto.Transactions;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;
import com.mastercard.fdx.mock.utilities.JwtDecoderUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
	
	public AccountDescriptor getAccountsDetails(String accountId) {
		return accountsRepository.findByAccountId(accountId);
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
	
}
