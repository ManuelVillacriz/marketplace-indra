package com.indra.marketplace.customer.controller;

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
import com.indra.marketplace.common.entity.Customer;
import com.indra.marketplace.customer.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController extends CommonController<Customer, CustomerService>{
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Validated @RequestBody Customer customer, BindingResult result, @PathVariable(name = "id") Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Customer> objeto = service.findById(id);
		
		if(objeto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Customer customerDb = objeto.get();
		customerDb.setNumberDocument(customer.getNumberDocument());
		customerDb.setFirstName(customer.getFirstName());
		customerDb.setLastName(customer.getLastName());
		customerDb.setEmail(customer.getEmail());
		customerDb.setStatus(customer.isStatus());		
						
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(customerDb));
	}	


}
