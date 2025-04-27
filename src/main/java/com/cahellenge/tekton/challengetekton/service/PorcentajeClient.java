package com.cahellenge.tekton.challengetekton.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PorcentajeClient {
    private boolean first = true;
    public BigDecimal obtenerPorcentaje() {
        if (first) {
            first = false;
            return new BigDecimal("0.15");
        }
        throw new RuntimeException("Fallo simulado");
    }
}
