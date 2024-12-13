package com.indra.marketplace.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NotNull(message = "El código no puede ser nulo.")
	@NotBlank(message = "El código no puede ser vacio.")
	private String code;
	
	@NotNull(message = "El nombre no puede ser nulo.")
	@NotBlank(message = "El nombre no puede ser vacio.")
	private String name;
	
	@NotNull(message = "La descripción no puede ser nulo.")
	@NotBlank(message = "La descripción no puede ser vacio.")
	private String description;
	
	

}
