package com.teddystore.exception.handler;

import com.teddystore.exception.CostumerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CostumerTrackerGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CostumerNotFoundException.class})
    public ResponseEntity<?> handleCostumerNotFound(CostumerNotFoundException costumerNotFoundException, WebRequest request) {
        return handleExceptionInternal(costumerNotFoundException,
                costumerNotFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
