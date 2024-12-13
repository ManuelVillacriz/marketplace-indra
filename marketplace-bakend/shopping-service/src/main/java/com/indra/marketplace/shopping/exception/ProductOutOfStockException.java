package com.indra.marketplace.shopping.exception;

public class ProductOutOfStockException extends CustomRuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductOutOfStockException(String message) {
        super(message);
    }
}