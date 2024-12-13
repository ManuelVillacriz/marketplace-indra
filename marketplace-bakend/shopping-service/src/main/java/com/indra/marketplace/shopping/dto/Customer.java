package com.indra.marketplace.shopping.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
	
	private Long id;
	private String numberDocument;
	private String firstName;
	private String lastName;
	private String email;	
	private boolean status;
}
