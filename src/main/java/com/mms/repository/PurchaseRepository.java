package com.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.mms.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	 @Modifying
	    @Transactional
	    void deleteByProductId(Integer productId);
   
}