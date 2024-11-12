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
    
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleExceptionThrownWhileAccessingData(ResourceAccessException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidResourceStateException.class)
    public ResponseEntity<String> handleInvalidResourceStateException(InvalidResourceStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
