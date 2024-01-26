package com.ex.customerinfo.exception;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @Mock
    private WebRequest mockWebRequest;

    @Test
    void handleResponseStatusException() {
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleResponseStatusException(ex, mockWebRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Resource not found", ((ErrorResponse) responseEntity.getBody()).getError());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getMessage()).thenReturn("Validation failed");
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleMethodArgumentNotValidException(ex, mockWebRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Validation failed", ((ErrorResponse) responseEntity.getBody()).getMessage());
        assertEquals("Bad Request", ((ErrorResponse) responseEntity.getBody()).getError());
    }

    @Test
    void handleBadRequestException() {
        BadRequestException ex = new BadRequestException("Invalid request");
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleBadRequestException(ex, mockWebRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid request", ((ErrorResponse) responseEntity.getBody()).getMessage());
        assertEquals("Bad Request", ((ErrorResponse) responseEntity.getBody()).getError());
    }

    @Test
    void handleAllExceptions() {
        Exception ex = new Exception("Something went wrong");
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleAllExceptions(ex, mockWebRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Something went wrong", ((ErrorResponse) responseEntity.getBody()).getMessage());
        assertEquals("Internal Server Error", ((ErrorResponse) responseEntity.getBody()).getError());
    }
}
