package com.indra.marketplace.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.marketplace.shopping.entity.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    public Coupon findByCode(String code);
}