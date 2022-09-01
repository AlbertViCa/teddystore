package com.teddystore.exception.handler;

import com.teddystore.exception.EmployeeNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmployeeTrackerGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    public ResponseEntity<?> handleEmployeeNotFound(EmployeeNotFoundException employeeNotFoundException, WebRequest request) {
        return handleExceptionInternal(employeeNotFoundException,
                employeeNotFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
