package com.indra.marketplace.shopping.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import lombok.Data;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number_invoice")
	private String numberInvoice;

	private String description;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "creation_date")	
	private LocalDateTime creationDate;

	@Valid
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_id")
	private List<InvoiceItem> items;

	@Transient
	private Customer customer;

	public Invoice() {
		items = new ArrayList<>();
	}

	@PrePersist
	public void prePersist() {
		this.creationDate =  LocalDateTime.now();
	}
}
