package com.indra.marketplace.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.indra.marketplace.shopping.dto.Product;


@FeignClient(name = "product-service", path = "/api/products",url = "${product-service.url}")
public interface ProductClient {
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> listById(@PathVariable("id") Long id);

	@PutMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable Long id, @RequestParam(name = "quantity", required = true) Integer quantity);
	
	@GetMapping("/{productId}/price")
    public Double getProductPrice(@PathVariable Long productId);
	
	@GetMapping("/{productId}/stock")
    public Integer getProductStock(@PathVariable Long productId);
}
