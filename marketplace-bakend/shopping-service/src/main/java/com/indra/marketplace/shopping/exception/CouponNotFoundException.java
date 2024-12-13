package com.indra.marketplace.shopping.exception;

public class CouponNotFoundException extends CustomRuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponNotFoundException(String message) {
        super(message);
    }
}