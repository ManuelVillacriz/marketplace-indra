package com.indra.marketplace.shopping.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.indra.marketplace.shopping.config.CustomDoubleSerializer;
import com.indra.marketplace.shopping.dto.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import lombok.Data;

@Data
@Entity
@Table(name = "carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Valid
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cart_id")
	private List<CartItem> items;

	@Transient
	private Customer customer;

	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double total;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id") 
	private Coupon coupon;

	public Double getTotal() {
		if (total == null) {
			total = items.stream().mapToDouble(CartItem::getSubTotal).sum();
		}
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Cart() {
		items = new ArrayList<>();
	}

	public void removeItem(Long productId) {
		items.removeIf(item -> item.getProductId().equals(productId));
	}

	@PrePersist
	public void prePersist() {
		this.creationDate = LocalDateTime.now();
	}

}
