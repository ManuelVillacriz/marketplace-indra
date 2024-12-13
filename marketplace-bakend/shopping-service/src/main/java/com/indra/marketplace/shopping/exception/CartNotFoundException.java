package com.indra.marketplace.shopping.exception;

public class CartNotFoundException extends CustomRuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartNotFoundException(String message) {
        super(message);
    }
}