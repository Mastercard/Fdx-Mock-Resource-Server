package com.mastercard.fdx.mock.controller;

import com.mastercard.fdx.mock.dto.AccountConsentResponse;
import com.mastercard.fdx.mock.entity.AccountConsent;
import com.mastercard.fdx.mock.dto.AccountListResponsePojo;
import com.mastercard.fdx.mock.dto.Accounts;
import com.mastercard.fdx.mock.dto.Page;
import com.mastercard.fdx.mock.service.AccountConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consent")
public class AccountConsentController {

	@Autowired
	AccountConsentService accountConsentService;
	
	@GetMapping("/accounts")
	public ResponseEntity<List<Accounts>> getAccountsByUserId(@RequestParam String userId,
															  @RequestHeader(value = "Authorization") String authorization){
		
		List<AccountListResponsePojo> list = accountConsentService.getAccountsByUserId(userId);
		Page page = new Page();
		page.setTotal(list.size());
		Accounts accounts = new Accounts();
		accounts.setAccounts(list);
		accounts.setPage(page);
		List<Accounts> responseList= new ArrayList<>();
		responseList.add(accounts);
		return new ResponseEntity<>(responseList,HttpStatus.OK);
		
	}
	
	@PostMapping("")
	public ResponseEntity<AccountConsentResponse> registerConsent(@RequestBody AccountConsent consent,
																  @RequestHeader(value = "Authorization") String authorization){
		return new ResponseEntity<>(accountConsentService.saveConsentAccount(consent),HttpStatus.OK);
		
	}
	
	@PutMapping("/{consentId}")
	public ResponseEntity<AccountConsentResponse> updateConsent(@RequestBody AccountConsent consent,
																@PathVariable(name = "consentId") String consentId,
																@RequestHeader(value = "Authorization") String authorization){
		return new ResponseEntity<>(accountConsentService.updateConsentAccount(consent,consentId),HttpStatus.OK);
		
	}
	
	@GetMapping("/{consentId}")
	public ResponseEntity<AccountConsent> getConsent(@PathVariable(name = "consentId") String consentId,
													 @RequestHeader(value = "Authorization") String authorization) {
		return new ResponseEntity<>(accountConsentService.getConsent(consentId),HttpStatus.OK);
		
	}
}
