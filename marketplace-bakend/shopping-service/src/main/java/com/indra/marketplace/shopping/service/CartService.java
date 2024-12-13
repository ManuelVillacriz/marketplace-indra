package com.indra.marketplace.shopping.service;

import java.time.LocalDate;

import com.indra.marketplace.common.service.CommonService;
import com.indra.marketplace.shopping.dto.ProductRequest;
import com.indra.marketplace.shopping.entity.Cart;

public interface CartService extends CommonService<Cart>{
	
	public Cart addProductToCart(Long customerId, ProductRequest requestProduct);
	
	public Cart updateProductQuantity(Long customerId, Long productId, Integer quantity);
	
	public Cart removeProductFromCart(Long customerId, Long productId);
	
	public  double calculatePriceWithDiscount(LocalDate purchaseDate, double price);

}
