package com.mastercard.fdx.mock.service;

import com.mastercard.fdx.mock.dto.AccountConsentResponse;
import com.mastercard.fdx.mock.repository.AccountConsentRepository;
import com.mastercard.fdx.mock.repository.AccountsRepository;
import com.mastercard.fdx.mock.entity.AccountConsent;
import com.mastercard.fdx.mock.entity.AccountDescriptor;
import com.mastercard.fdx.mock.dto.AccountListResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AccountConsentService {
	
	@Autowired
	AccountConsentRepository accountConsentRepository;
	
	@Autowired
	AccountsRepository accountsRepository;

	public List<AccountListResponsePojo> getAccountsByUserId(String userId){
		
		AccountConsent consent = accountConsentRepository.findAccountsByUserId(userId);
		
		if(Objects.nonNull(consent))
		{
			List<AccountDescriptor> list =	accountsRepository.findByAccountIds(consent.getAllAccountIds());

            return list.stream().map(s-> new AccountListResponsePojo(s.getAccountCategory(), s.getInstitutionAccountId(),
					s.getAccountType(), s.getNickname(), s.getStatus(), s.getBalanceAsOf(), s.getAccountNumber(),
					s.getAccountNumberDisplay(), s.getProductName(), s.getDescription(), s.getAccountOpenDate(), s.getAccountCloseDate(),s.getCurrentBalance(),
					s.getOpeningDayBalance(),s.getPrincipalBalance(),s.getAvailableCashBalance())).collect(Collectors.toList());
		}
		return null;
	}
	
	public AccountConsentResponse saveConsentAccount(AccountConsent accountConsent) {
		Optional<AccountConsent> accountsFromDB = Optional.ofNullable(accountConsentRepository.findAccountsByUserId(accountConsent.getUserId()));
		String newConsentId = UUID.randomUUID().toString();
		return getAccountConsentResponse(accountConsent, accountsFromDB, newConsentId);
	}

	public AccountConsentResponse updateConsentAccount(AccountConsent consent, String consentId) {
		Optional<AccountConsent> accountsFromDB = accountConsentRepository.findAccountsByConsentId(consentId);
		return getAccountConsentResponse(consent, accountsFromDB, null);
	}

	private AccountConsentResponse getAccountConsentResponse(AccountConsent accountConsent,
			Optional<AccountConsent> accountsFromDB, String newConsentId) {
		if(accountsFromDB.isPresent()) {
			if(newConsentId != null) {
				accountsFromDB.get().setConsentId(newConsentId);
			}
			accountsFromDB.get().setAccountIds(accountConsent.getAccountIds());
			accountsFromDB.get().setConsentShareDurationSeconds(accountConsent.getConsentShareDurationSeconds());
			AccountConsent response = accountConsentRepository.save(accountsFromDB.get());
			AccountConsentResponse accountConsentResponse = new AccountConsentResponse();
			accountConsentResponse.setEndDate(new Timestamp(
					System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(response.getConsentShareDurationSeconds())));
			accountConsentResponse.setCustomerId(response.getUserId());
			accountConsentResponse.setConsentId(response.getConsentId());
			return accountConsentResponse;
		}
		return null;
	}

	public AccountConsent getConsent(String consentId) {
		Optional<AccountConsent> accountsFromDB = accountConsentRepository.findAccountsByConsentId(consentId);
		if(Objects.nonNull(accountsFromDB) &&  accountsFromDB.isPresent()) {
			return accountsFromDB.get();
		}
		else {
			AccountConsent errorConsent = new AccountConsent();
			errorConsent.setErrorCode(2001);
			errorConsent.setErrorMessage("No Consent Found for "+consentId+" consentId");
			return errorConsent;
		}
	}
}
