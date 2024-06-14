package com.mastercard.fdx.mock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mastercard.fdx.mock.entity.FdxUser;

@Repository
public interface FdxUserRepository extends JpaRepository<FdxUser, Integer> {

	FdxUser findByUserId(String userId);
}
