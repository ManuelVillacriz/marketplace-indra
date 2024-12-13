package com.indra.marketplace.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indra.marketplace.common.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p.price FROM Product p WHERE p.id = :productId")
	Double findPriceByProductId(@Param("productId") Long productId);

	@Query("SELECT p.stock FROM Product p WHERE p.id = :productId")
	Integer findStockByProductId(@Param("productId") Long productId);
}
