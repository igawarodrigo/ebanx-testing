package com.ebanx.homeassigment.assignment.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { AccountNotFoundException.class })
    protected ResponseEntity<Object> handleAccountNotFoundException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(0);
    }

    @ExceptionHandler(value = { NotEnoughAmountException.class })
    protected ResponseEntity<Object> handleNotEnoughAmountException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(0);
    }
}
