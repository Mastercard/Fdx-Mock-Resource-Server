package com.mastercard.fdx.mock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.entity.AccountConsent;

@Repository
public interface AccountConsentRepository extends JpaRepository<AccountConsent, Integer> {

	@Query("select ac from AccountConsent ac where ac.userId=?1")
	AccountConsent findAccountsByUserId(String userId);

	@Query("select ac from AccountConsent ac where ac.consentId=?1")
	Optional<AccountConsent> findAccountsByConsentId(String consentId);
		
}
