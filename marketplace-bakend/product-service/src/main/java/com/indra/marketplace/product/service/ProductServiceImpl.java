package com.indra.marketplace.product.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.indra.marketplace.common.entity.Product;
import com.indra.marketplace.common.service.CommonServiceImpl;
import com.indra.marketplace.product.exception.ProductOutOfStockException;
import com.indra.marketplace.product.repository.ProductRepository;

@Service
public class ProductServiceImpl extends CommonServiceImpl<Product, ProductRepository> implements ProductService {

	@Override
	public Product updateStock(Long id, Integer quantity) {

		Product product = findById(id).orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));

		int updatedStock = product.getStock() + quantity;
		
		if (updatedStock < 0) {
			throw new ProductOutOfStockException("La cantidad enviada, es superior a las cantidades existentes ");
		}

		product.setStock(updatedStock);
		
		return repository.save(product);
	}

	@Override
	public Double findPriceByProductId(Long productId) {
		return repository.findPriceByProductId(productId);
	}

	@Override
	public Integer findStockByProductId(Long productId) {		
		 return repository.findStockByProductId(productId);
	}
	
	

}
