package com.givts.app.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.givts.app.exception.ResourceAlreadyExistsException;
import com.givts.app.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CrudControllerBase {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleBadJSONException(HttpMessageNotReadableException ex) throws IOException {
        Map<String, Object> error = new HashMap<>();
        if (ex.getCause() instanceof InvalidFormatException) {
            error.put("message", "Bad format for value. For dates, please use yyyy-MM-dd");
        } else {
            error.put("message", "Exception has occured");
            error.put("systemMessage", ex.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body(error);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public Map<String, Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return error;
    }
}
