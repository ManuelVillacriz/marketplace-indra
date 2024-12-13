package com.indra.marketplace.shopping.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private Long productId;
    private Integer quantity;
}