package com.indra.marketplace.product.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indra.marketplace.common.controller.CommonController;
import com.indra.marketplace.common.entity.Product;
import com.indra.marketplace.product.service.ProductService;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController extends CommonController<Product, ProductService> {

	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Validated @RequestBody Product product, BindingResult result, @PathVariable(name = "id") Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Product> objeto = service.findById(id);
		
		if(objeto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Product productDb = objeto.get();
		productDb.setCode(product.getCode());
		productDb.setName(product.getName());
		productDb.setDescription(product.getDescription());
		productDb.setDescription(product.getDescription());
		
		productDb.setPrice(product.getPrice());
		productDb.setMarca(product.getMarca());
		productDb.setStatus(product.isStatus());
						
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(productDb));
	}
	
	
	 @PutMapping (value = "/{id}/stock")
	    public ResponseEntity<Product> updateStockProduct(@PathVariable  Long id ,@RequestParam(name = "quantity", required = true) Integer quantity){
	        Product product = service.updateStock(id, quantity);
	        if (product == null){
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(product);
	  }	 
	 

	    @GetMapping("/{productId}/price")
	    public ResponseEntity<Double> getProductPrice(@PathVariable Long productId) {
	        Double price = service.findPriceByProductId(productId);

	        if (price == null) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok(price);
	    }
	    
	    @GetMapping("/{productId}/stock")
	    public ResponseEntity<Integer> getProductStock(@PathVariable Long productId) {
	    	 
	    	Integer stock = service.findStockByProductId(productId);
	    	
	    	if (stock == null) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok(stock);
	    }
	    
	    
	 
	 
}
