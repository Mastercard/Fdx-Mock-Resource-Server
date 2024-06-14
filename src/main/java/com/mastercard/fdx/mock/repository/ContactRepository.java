package com.mastercard.fdx.mock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.entity.AccountContact;

@Repository
public interface ContactRepository extends JpaRepository<AccountContact, Integer>{

	@Query("select ad from  AccountContact ad where ad.accountId =?1")
	AccountContact findByAccountId(String accountId);

}
