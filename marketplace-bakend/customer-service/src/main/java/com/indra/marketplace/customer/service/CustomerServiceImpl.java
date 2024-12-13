package com.indra.marketplace.customer.service;


import org.springframework.stereotype.Service;

import com.indra.marketplace.common.entity.Customer;
import com.indra.marketplace.common.service.CommonServiceImpl;
import com.indra.marketplace.customer.repository.CustomerRepository;


@Service
public class CustomerServiceImpl extends CommonServiceImpl<Customer, CustomerRepository> implements CustomerService {
	
	

}
