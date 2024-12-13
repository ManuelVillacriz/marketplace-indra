package com.indra.marketplace.common.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Category category;
	
	@NotNull(message = "El código no puede ser nulo.")
	@NotBlank(message = "El código no puede ser vacio.")
	private String code;
	
	@NotNull(message = "El nombre no puede ser nulo.")
	@NotBlank(message = "El nombre no puede ser vacio.")
	private String name;
	
	@NotNull(message = "La descripción no puede ser nulo.")
	@NotBlank(message = "La descripción no puede ser vacio.")
	private String description;
	
	
	 @Min(value = 1, message = "El stock debe ser mayor o igual a 1")
	 private int stock;
	 
	 @Min(value = 0, message = "EL precio debe ser mayor a cero")
	 private double price;
	 
	 private String marca;
	
	private boolean status;
	
	@Column(name = "aplica_descuento")
	private boolean aplicaDesceunto;
	
	@Column(name = "creation_date")	
	private LocalDateTime creationDate;
	
	@PrePersist
    protected void onCreate() {
        this.status = true;
        this.creationDate =  LocalDateTime.now(); 
    }

}
