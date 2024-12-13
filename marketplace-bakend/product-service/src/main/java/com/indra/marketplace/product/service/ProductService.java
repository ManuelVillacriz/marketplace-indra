package com.indra.marketplace.product.service;

import org.springframework.data.repository.query.Param;

import com.indra.marketplace.common.entity.Product;
import com.indra.marketplace.common.service.CommonService;

public interface ProductService extends CommonService<Product> {
	
	public Product updateStock(Long id, Integer quantity);
	
	public Double findPriceByProductId(Long productId);
	
	public Integer findStockByProductId(@Param("productId") Long productId);
}
