package com.indra.marketplace.shopping.service;

import com.indra.marketplace.common.service.CommonService;
import com.indra.marketplace.shopping.entity.Cart;
import com.indra.marketplace.shopping.entity.Coupon;

public interface CouponService extends CommonService<Coupon>{	
	
	public Cart applyCouponToCart(String couponCode, Long cartId);

}
