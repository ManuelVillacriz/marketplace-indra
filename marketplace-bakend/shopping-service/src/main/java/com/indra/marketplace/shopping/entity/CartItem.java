package com.indra.marketplace.shopping.entity;

import com.indra.marketplace.shopping.dto.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
@Table(name = "cart_items")
public class CartItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "product_id")
    private Long productId;
	
	@Positive(message = "La cantidad debe ser mayor que cero")
    private Integer quantity;
    
    private Double  price;
    
    @Transient
    private Double subTotal;

    @Transient
    private Product product;
    
    public Double getSubTotal(){
        if (this.price >0  && this.quantity >0 ){
            return this.quantity * this.price;
        }else {
            return (double) 0;
        }
    }
    
    public CartItem(){
        this.quantity=(int) 0;
        this.price=(double) 0;

    }
}
