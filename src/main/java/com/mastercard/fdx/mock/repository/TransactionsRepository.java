package com.mastercard.fdx.mock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsDetails, Integer> {

	@Query("select ad from  TransactionsDetails ad where ad.accountId =?1")
	List<TransactionsDetails> findTransactionsByAccountId(String accountId);

	
}
