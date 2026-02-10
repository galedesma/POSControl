package com.galedesma.poscontrol.handler;

import com.galedesma.poscontrol.dto.out.ErrorResponse;
import com.galedesma.poscontrol.exception.PointOfSaleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(PointOfSaleNotFoundException.class)
    public ResponseEntity<ErrorResponse> pointOfSaleNotFound(PointOfSaleNotFoundException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
