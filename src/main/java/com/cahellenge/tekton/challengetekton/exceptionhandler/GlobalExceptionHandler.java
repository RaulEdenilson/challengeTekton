package com.cahellenge.tekton.challengetekton.exceptionhandler;

import com.cahellenge.tekton.challengetekton.service.HistorialService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HistorialService historial;

    public GlobalExceptionHandler(HistorialService historial) {
        this.historial = historial;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex,
                                                               HttpServletRequest req) {
        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        historial.registrarAsync(req.getRequestURI(), null, null, null, errorMsg);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", errorMsg));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> handleParseError(HttpMessageNotReadableException ex,
                                                               HttpServletRequest req) {
        String errorMsg = "Formato inválido: num1 y num2 deben ser números";
        historial.registrarAsync(req.getRequestURI(), null, null, null, errorMsg);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", errorMsg));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String,String>> handleIllegalState(IllegalStateException ex,
                                                                 HttpServletRequest req) {
        String errorMsg = ex.getMessage();
        historial.registrarAsync(req.getRequestURI(), null, null, null, errorMsg);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", errorMsg));
    }
}