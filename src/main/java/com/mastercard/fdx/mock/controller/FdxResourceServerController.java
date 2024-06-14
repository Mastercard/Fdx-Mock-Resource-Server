package com.mastercard.fdx.mock.controller;

import com.mastercard.fdx.mock.exception.ApiException;
import com.mastercard.fdx.mock.entity.AccountContact;
import com.mastercard.fdx.mock.entity.AccountDescriptor;
import com.mastercard.fdx.mock.dto.AccountPaymentNetworkList;
import com.mastercard.fdx.mock.dto.Accounts;
import com.mastercard.fdx.mock.service.FdxResourceService;
import com.mastercard.fdx.mock.transaction.dto.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fdx/v6")
public class FdxResourceServerController {

	 
	 @Autowired
	 FdxResourceService fdxResourceService;
	   
	@GetMapping("/accounts")
	public Accounts getAccountsList(@RequestHeader(value = "Authorization") String authorization ){
		return fdxResourceService.getAccountsList(authorization);
	}
	
	@GetMapping("/accounts/{accountId}")
	public ResponseEntity<AccountDescriptor> getAccountsDetails(@RequestHeader(value = "Authorization") String authorization,
																@PathVariable("accountId") String accountId){
		try {
			fdxResourceService.validateTokenWithRequestAccountId(authorization,accountId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new AccountDescriptor(e.getErrorPojo().getErrorCode(),e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}
		AccountDescriptor response = fdxResourceService.getAccountsDetails(accountId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	

	@GetMapping("/accounts/{accountId}/contact")
	public ResponseEntity<AccountContact> getContactsDetails(@RequestHeader(value = "Authorization") String authorization,
															 @PathVariable("accountId") String accountId){
		try {
			fdxResourceService.validateTokenWithRequestAccountId(authorization,accountId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new AccountContact(e.getErrorPojo().getErrorCode(),e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(fdxResourceService.getContactsDetails(accountId),HttpStatus.OK);
	}
	
	@GetMapping("/accounts/{accountId}/transactions")
	public ResponseEntity<Transactions> getTransactions(@RequestHeader(value = "Authorization") String authorization,
														@PathVariable("accountId") String accountId,
			@RequestHeader(value = "startTime", required = false) String startTime,
			@RequestHeader(value = "endTime", required = false) String endTime){
		
		try {
			fdxResourceService.validateTokenWithRequestAccountId(authorization,accountId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new Transactions(e.getErrorPojo().getErrorCode(),e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(fdxResourceService.getTransactions(accountId),HttpStatus.OK);
	
	}
	
	@GetMapping("/accounts/{accountId}/payment-networks")
	public ResponseEntity<AccountPaymentNetworkList> getPaymentNetworksDetails(@RequestHeader(value = "Authorization") String authorization,
																			   @PathVariable("accountId") String accountId){
		
		try {
			fdxResourceService.validateTokenWithRequestAccountId(authorization,accountId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new AccountPaymentNetworkList(e.getErrorPojo().getErrorCode(),e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(fdxResourceService.getPaymentNetworksDetails(accountId),HttpStatus.OK);
		
	}

}
