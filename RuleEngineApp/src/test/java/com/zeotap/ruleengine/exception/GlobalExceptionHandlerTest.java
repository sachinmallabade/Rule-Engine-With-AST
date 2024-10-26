package com.zeotap.ruleengine.exception;

import com.zeotap.ruleengine.Exception.ErrorResponse;
import com.zeotap.ruleengine.Exception.GlobalExceptionHandler;
import com.zeotap.ruleengine.Exception.RuleNotFoundException;
import com.zeotap.ruleengine.Exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void handleRuleNotFoundException_ShouldReturnNotFound() {
        RuleNotFoundException exception = new RuleNotFoundException("Rule not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuleNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Rule not found", response.getBody());
    }

    @Test
    public void handleUserNotFoundException_ShouldReturnNotFound() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void handleIllegalArgument_ShouldReturnBadRequest() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgument(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody());
    }

    @Test
    public void handleGenericException_ShouldReturnInternalServerError() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Unexpected error", response.getBody());
    }
}
