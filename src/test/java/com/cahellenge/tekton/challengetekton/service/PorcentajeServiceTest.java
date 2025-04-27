package com.cahellenge.tekton.challengetekton.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // 🔥 importante
class PorcentajeServiceTest {

    @Mock
    private PorcentajeClient porcentajeClient;

    @InjectMocks
    private PorcentajeService porcentajeService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetPorcentaje() {
        // Arrange
        BigDecimal expectedPorcentaje = new BigDecimal("15.75");
        when(porcentajeClient.obtenerPorcentaje()).thenReturn(expectedPorcentaje);

        // Act
        BigDecimal porcentaje = porcentajeService.getPorcentaje();

        // Assert
        assertEquals(expectedPorcentaje, porcentaje);
        verify(porcentajeClient, times(1)).obtenerPorcentaje();
    }
}
