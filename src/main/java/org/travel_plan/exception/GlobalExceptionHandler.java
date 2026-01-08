package org.travel_plan.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoResultFoundException.class)
    public ResponseEntity<String> NoResultExceptionHandler(NoResultFoundException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}

