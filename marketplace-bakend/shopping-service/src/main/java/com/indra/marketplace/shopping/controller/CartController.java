package com.indra.marketplace.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indra.marketplace.common.controller.CommonController;
import com.indra.marketplace.shopping.dto.CouponRequest;
import com.indra.marketplace.shopping.dto.ProductQuantityRequest;
import com.indra.marketplace.shopping.dto.ProductRequest;
import com.indra.marketplace.shopping.entity.Cart;
import com.indra.marketplace.shopping.service.CartService;
import com.indra.marketplace.shopping.service.CouponService;

@RestController
@RequestMapping(value = "/api/carts")
public class CartController extends CommonController<Cart, CartService> {

	@Autowired
	private CouponService couponService;

	@PostMapping("/{customerId}/products")
	public ResponseEntity<Cart> addProductToCart(@PathVariable Long customerId,
			@RequestBody ProductRequest requestProduct) {
		Cart updatedCart = service.addProductToCart(customerId, requestProduct);
		return ResponseEntity.ok(updatedCart);
	}

	@PutMapping("/{customerId}/products/{productId}")
	public ResponseEntity<Cart> updateProductQuantity(@PathVariable Long customerId, @PathVariable Long productId,
			@RequestBody ProductQuantityRequest request) {
		Cart updatedCart = service.updateProductQuantity(customerId, productId, request.getQuantity());
		return ResponseEntity.ok(updatedCart);
	}

	@DeleteMapping("/{customerId}/products/{productId}")
	public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long customerId, @PathVariable Long productId) {

		Cart cart = service.removeProductFromCart(customerId, productId);
		return ResponseEntity.ok(cart);
	}

	@PutMapping("/{cartId}/apply-coupon")
	public ResponseEntity<Cart> applyCouponToCart(@PathVariable Long cartId, @RequestBody CouponRequest couponRequest) {

		Cart updatedCart = couponService.applyCouponToCart(couponRequest.getCouponCode(), cartId);
		return ResponseEntity.ok(updatedCart);

	}
}
