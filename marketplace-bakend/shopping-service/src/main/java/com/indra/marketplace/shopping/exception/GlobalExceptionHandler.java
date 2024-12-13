package com.indra.marketplace.shopping.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<Map<String, String>> handleCustomRuntimeException(CustomRuntimeException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; 

        if (ex instanceof ProductOutOfStockException) {
            status = HttpStatus.BAD_REQUEST;
        }else if (ex instanceof ProductNoFoundException) {
            status = HttpStatus.NOT_FOUND;
        }else if (ex instanceof ProductNoFoundCartException) {
            status = HttpStatus.NOT_FOUND;
        }else if (ex instanceof CouponNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }else if (ex instanceof CouponUsedException) {
            status = HttpStatus.BAD_REQUEST;
        }else if (ex instanceof CouponNotValidException) {
            status = HttpStatus.BAD_REQUEST;
        }else if (ex instanceof CartNotFoundException) {
            status = HttpStatus.BAD_REQUEST;
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());

        return ResponseEntity.status(status).body(response);
    }
}