package com.cahellenge.tekton.challengetekton.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@ExtendWith(MockitoExtension.class)
class CalculadoraServiceCacheNullTest {

    @Mock
    private PorcentajeService porcentajeService;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private CalculadoraService calculadoraService;

    @BeforeEach
    void setUp() {
        when(cacheManager.getCache("porcentaje")).thenReturn(null);
    }

    @Test
    void testCacheNoDisponible_LanzaExcepcion() {

        when(porcentajeService.getPorcentaje()).thenThrow(new RuntimeException("Servicio no disponible"));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            calculadoraService.calcular(BigDecimal.ONE, BigDecimal.ONE);
        });

        assertEquals("El caché 'porcentaje' no está disponible", exception.getMessage());
    }
}