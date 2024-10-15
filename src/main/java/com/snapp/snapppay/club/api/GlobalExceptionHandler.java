package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.exception.DataNotFoundException;
import com.snapp.snapppay.club.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException exception) {
        logError(exception);
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handle(ValidationException validationException) {
        logError(validationException);
        return new ResponseEntity<>(validationException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Pair<String, Long>> handle(DataNotFoundException dataNotFoundException) {
        logError(dataNotFoundException);
        return new ResponseEntity<>(Pair.of(dataNotFoundException.getType(), dataNotFoundException.getId()), HttpStatus.BAD_REQUEST);
    }

    private void logError(Exception e) {
        log.error(e.getMessage(), e);
    }

}
