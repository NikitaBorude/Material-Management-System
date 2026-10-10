package com.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mms.model.SaleItem;

@Repository
	public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {

	@Modifying
    @Transactional
    void deleteByProduct_Id(Integer productId);
	}
