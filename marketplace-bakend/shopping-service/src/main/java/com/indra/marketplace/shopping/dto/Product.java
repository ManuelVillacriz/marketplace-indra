package com.indra.marketplace.shopping.dto;

import lombok.Data;

@Data
public class Product {
	
	private Long id;
	private Category category;
	private String code;
	private String name;
	private String description;	
	private Integer stock;
	private Double price;
	private String marca;	
	private String status;	
	private boolean aplicaDescuento;
}
