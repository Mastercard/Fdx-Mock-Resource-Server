package com.mastercard.fdx.mock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.entity.AccountDescriptor;

@Repository
public interface AccountsRepository extends JpaRepository<AccountDescriptor, Integer> {

	@Query("select ad from  AccountDescriptor ad where ad.institutionAccountId =?1")
	AccountDescriptor findByAccountId(String accountId);
	
	@Query("select ad from  AccountDescriptor ad")
	List<AccountDescriptor> findListOfAccounts();

	@Query("select ad from  AccountDescriptor ad where ad.institutionAccountId IN (?1)")
	List<AccountDescriptor> findByAccountIds(List<String> accountId);
	
}
