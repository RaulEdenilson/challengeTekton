package com.cahellenge.tekton.challengetekton.exceptionhandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import com.cahellenge.tekton.challengetekton.service.HistorialService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HistorialService historialService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn("/test-endpoint");
    }

    @Test
    void testHandleValidation() {
        FieldError fieldError = new FieldError("objectName", "num1", "must not be null");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("num1: must not be null", response.getBody().get("error"));

        verify(historialService, times(1))
                .registrarAsync(eq("/test-endpoint"), isNull(), isNull(), isNull(), eq("num1: must not be null"));
    }

    @Test
    void testHandleParseError() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Malformed JSON request");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleParseError(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Formato inválido: num1 y num2 deben ser números", response.getBody().get("error"));

        verify(historialService, times(1))
                .registrarAsync(eq("/test-endpoint"), isNull(), isNull(), isNull(), eq("Formato inválido: num1 y num2 deben ser números"));
    }

    @Test
    void testHandleIllegalState() {
        IllegalStateException ex = new IllegalStateException("No hay porcentaje disponible");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleIllegalState(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("No hay porcentaje disponible", response.getBody().get("error"));

        verify(historialService, times(1))
                .registrarAsync(eq("/test-endpoint"), isNull(), isNull(), isNull(), eq("No hay porcentaje disponible"));
    }
}