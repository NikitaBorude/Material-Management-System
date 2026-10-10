package com.mms.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.mms.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
