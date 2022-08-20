package com.teddystore.exception.handler;

import com.teddystore.exception.TeddyNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TeddyTrackerGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TeddyNotFoundException.class})
    public ResponseEntity<?> handleTeddyNotFound(TeddyNotFoundException teddyNotFoundException, WebRequest request) {
        return handleExceptionInternal(teddyNotFoundException,
                teddyNotFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
