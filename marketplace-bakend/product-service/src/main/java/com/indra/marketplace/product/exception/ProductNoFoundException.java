package com.indra.marketplace.product.exception;

public class ProductNoFoundException extends CustomRuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNoFoundException(String message) {
        super(message);
    }
}