package com.mastercard.fdx.mock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.entity.LineOfCreditAccount;

@Repository
public interface LineOfCreditAccountsRepository extends JpaRepository<LineOfCreditAccount, Integer> {

	
}
