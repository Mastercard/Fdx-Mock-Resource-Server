package com.mastercard.fdx.mock.controller;

import com.mastercard.fdx.mock.exception.ApiException;
import com.mastercard.fdx.mock.entity.AccountContact;
import com.mastercard.fdx.mock.entity.AccountDescriptor;
import com.mastercard.fdx.mock.dto.AccountPaymentNetworkList;
import com.mastercard.fdx.mock.dto.Accounts;
import com.mastercard.fdx.mock.dto.StatementsResponse;
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

	/**
	 * Below API will return list of consented accounts against the requested OAuth token.
	 * @param authorization
	 * @return
	 */
	@GetMapping("/accounts")
	public Accounts getAccountsList(@RequestHeader(value = "Authorization") String authorization ){
		return fdxResourceService.getAccountsList(authorization);
	}

	/**
	 * Below API will return account details for the accountId against the requested OAuth token.
	 * @param authorization
	 * @param accountId
	 * @return
	 */
	@GetMapping("/accounts/{accountId}")
	public ResponseEntity<AccountDescriptor> getAccountsDetails(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("accountId") String accountId){
		try {
			fdxResourceService.validateTokenWithRequestAccountId(authorization,accountId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new AccountDescriptor(e.getErrorPojo().getErrorCode(),e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}
		return fdxResourceService.getAccountsDetails(accountId);
	}

	/**
	 * Below API will return account's contact details for the accountId against the requested OAuth token.
	 * @param authorization
	 * @param accountId
	 * @return
	 */
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

	/**
	 * Below API will return account's transactions for the accountId against the requested OAuth token.
	 * @param authorization
	 * @param accountId
	 * @return
	 */
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

	/**
	 * Below API will return payment network details associated with accountId against the requested OAuth token.
	 * @param authorization
	 * @param accountId
	 * @return
	 */
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

	/**
	 * Below API will return list of statements associated with accountId against the requested OAuth token.
	 * @param authorization
	 * @param accountId
	 * @return
	 */
	@GetMapping("/accounts/{accountId}/statements")
	public ResponseEntity<StatementsResponse> getStatements(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("accountId") String accountId){

		try {
			fdxResourceService.validateTokenWithRequestAccountId(authorization,accountId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new StatementsResponse(e.getErrorPojo().getErrorCode(),e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}

		return fdxResourceService.getStatements(accountId);

	}

	/**
	 * Below API will return statement pdf associated with accountId, statementId against the requested OAuth token.
	 * @param authorization
	 * @param accountId
	 * @return
	 */
	@GetMapping("/accounts/{accountId}/statements/{statementId}")
	public ResponseEntity<Object> getStatementsByStatementId(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("accountId") String accountId,@PathVariable("statementId") String statementId){

		try {
			fdxResourceService.validateTokenWithRequestAccountId(authorization,accountId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new StatementsResponse(e.getErrorPojo().getErrorCode(),e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}

		return fdxResourceService.getStatementsByStatementId(accountId,statementId);

	}

}
