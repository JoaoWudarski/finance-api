package com.jwapp.financialapi.controller.handler;

import com.jwapp.financialapi.exception.ConflictException;
import com.jwapp.financialapi.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getFieldErrors().forEach(x -> fieldErrors.put(x.getField(), x.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ErrorResponse("Validation request error", fieldErrors));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleThrowable(Throwable ex) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage(), null));
    }
}
