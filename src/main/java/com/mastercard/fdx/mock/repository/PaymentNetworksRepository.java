package com.mastercard.fdx.mock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.entity.AccountPaymentNetwork;

@Repository
public interface PaymentNetworksRepository extends JpaRepository<AccountPaymentNetwork, Integer>{

	@Query("select ad from  AccountPaymentNetwork ad where ad.accountId =?1")
	List<AccountPaymentNetwork> findByAccountId(String accountId);
}
