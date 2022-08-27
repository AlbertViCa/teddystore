package com.teddystore.exception.handler;

import com.teddystore.exception.OrderNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OrderTrackerGlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {OrderNotFoundException.class})
    public ResponseEntity<?> handleTeddyNotFound(OrderNotFoundException orderNotFoundException, WebRequest request) {
        return handleExceptionInternal(orderNotFoundException,
                orderNotFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
