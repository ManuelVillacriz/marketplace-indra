package com.indra.marketplace.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.marketplace.shopping.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	 Optional<Cart> findByCustomerId(Long customerId);

}
