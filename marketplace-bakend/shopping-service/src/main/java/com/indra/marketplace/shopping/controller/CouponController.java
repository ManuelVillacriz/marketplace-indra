package com.indra.marketplace.shopping.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indra.marketplace.common.controller.CommonController;
import com.indra.marketplace.shopping.entity.Coupon;
import com.indra.marketplace.shopping.service.CouponService;

@RestController
@RequestMapping(value = "/api/coupons")
public class CouponController extends CommonController<Coupon, CouponService> {
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Validated @RequestBody Coupon coupon, BindingResult result, @PathVariable(name = "id") Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Coupon> objeto = service.findById(id);
		
		if(objeto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Coupon couponDb = objeto.get();
		
		couponDb.setCode(coupon.getCode());
		couponDb.setDiscount(coupon.getDiscount());
		couponDb.setStartDate(coupon.getStartDate());
		couponDb.setEndDate(coupon.getEndDate());
		
						
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(couponDb));
	}

}
