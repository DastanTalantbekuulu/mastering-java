package com.coffee.sale.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, String>> handleExceptionsMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errorResponse = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            try {
                Class<?> beanClazz = Objects.requireNonNull(ex.getTarget()).getClass();
                Field field = beanClazz.getDeclaredField(fieldError.getField());
                JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
                if (jsonPropertyAnnotation != null) {
                    errorResponse.put(jsonPropertyAnnotation.value(), fieldError.getDefaultMessage());
                } else {
                    errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
            } catch (NoSuchFieldException e) {
                errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SQLException.class)
    public final ResponseEntity<String> handleSQLException(SQLException sqle) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
