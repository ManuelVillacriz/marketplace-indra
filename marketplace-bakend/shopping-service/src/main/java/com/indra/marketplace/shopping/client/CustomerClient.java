package com.indra.marketplace.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.indra.marketplace.shopping.dto.Customer;

@FeignClient(name="customer-service", path = "/api/customers", url = "${customer-service.url}")
public interface CustomerClient {
		
	@GetMapping(value = "/{id}")	
	public ResponseEntity<Customer> listById(@PathVariable("id") long id);
	
	
}