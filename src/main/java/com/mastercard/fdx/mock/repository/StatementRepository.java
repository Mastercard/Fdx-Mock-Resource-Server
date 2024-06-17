package com.mastercard.fdx.mock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.entity.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Integer>{

	@Query("select ad from  Statement ad where ad.accountId =?1")
	List<Statement> findStatementByAccountId(String accountId);
	
	@Query("select ad from  Statement ad where ad.statementId =?1")
	Statement findStatementByStatementId(String statementId);

}
