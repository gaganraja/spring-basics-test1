package com.example.authenticatingldap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
	
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNonNumericParamException(NumberFormatException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataAccessException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
