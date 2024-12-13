package com.indra.marketplace.shopping.exception;

public class ProductNoFoundCartException extends CustomRuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNoFoundCartException(String message) {
        super(message);
    }
}