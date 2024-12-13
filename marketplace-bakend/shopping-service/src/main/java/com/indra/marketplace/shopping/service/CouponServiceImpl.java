package com.indra.marketplace.shopping.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indra.marketplace.common.service.CommonServiceImpl;
import com.indra.marketplace.shopping.entity.Cart;
import com.indra.marketplace.shopping.entity.Coupon;
import com.indra.marketplace.shopping.exception.CartNotFoundException;
import com.indra.marketplace.shopping.exception.CouponNotFoundException;
import com.indra.marketplace.shopping.exception.CouponNotValidException;
import com.indra.marketplace.shopping.exception.CouponUsedException;
import com.indra.marketplace.shopping.repository.CartRepository;
import com.indra.marketplace.shopping.repository.CouponRepository;

@Service
public class CouponServiceImpl extends CommonServiceImpl<Coupon, CouponRepository> implements CouponService{
	
	@Autowired
	private CartRepository cartRepository;
	
	public Cart applyCouponToCart(String couponCode, Long cartId) {
		
	    Cart cart = cartRepository.findById(cartId)
	            .orElseThrow(() -> new CartNotFoundException("Carrito no encontrado con ID: " + cartId));

	    Coupon coupon = repository.findByCode(couponCode);

	    if (coupon == null) {
	        throw new CouponNotFoundException("El cupón no existe.");
	    }

	    LocalDate currentDate = LocalDate.now();
	    if (coupon.getStartDate().isAfter(currentDate) || coupon.getEndDate().isBefore(currentDate)) {
	        throw new CouponNotValidException("El cupón no es válido en esta fecha.");
	    }

	    if (coupon.isUsed()) {
	        throw new CouponUsedException("El cupón ya ha sido utilizado.");
	    }

	    double totalAmount = cart.getTotal();

	    double discountAmount = (coupon.getDiscount() / 100) * totalAmount;

	    double newTotal = totalAmount - discountAmount;

	    coupon.setUsed(true);
	    repository.save(coupon);

	    cart.setTotal(newTotal);
	    cart.setCoupon(coupon);
	    
	    cartRepository.save(cart);

	    return cart;
	}
}
