package com.cahellenge.tekton.challengetekton.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HistorialEntry {
    private LocalDateTime timestamp;
    private String endpoint;
    private BigDecimal num1;
    private BigDecimal num2;
    private BigDecimal resultado;
    private String error;
}