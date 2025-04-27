package com.cahellenge.tekton.challengetekton.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import com.cahellenge.tekton.challengetekton.config.CacheConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest
@EnableCaching
@Import(CacheConfig.class)
class CalculadoraServiceTest {

    @MockitoBean
    private PorcentajeClient porcentajeClient;

    @Autowired
    private CalculadoraService calculadoraService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private PorcentajeService porcentajeService;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("porcentaje").clear();
    }

    @Test
    void testSeAlmacenaEnCache() {
        when(porcentajeClient.obtenerPorcentaje())
                .thenReturn(new BigDecimal("0.10"));

        calculadoraService.calcular(BigDecimal.ONE, BigDecimal.ONE);
        calculadoraService.calcular(BigDecimal.ONE, BigDecimal.ONE);

        verify(porcentajeClient, times(1)).obtenerPorcentaje();
    }
    @Test
    void cuandoLlamoDosVeces_getPorcentaje_cacheaResultado() {
        when(porcentajeClient.obtenerPorcentaje())
                .thenReturn(new BigDecimal("0.25"));

        BigDecimal p1 = porcentajeService.getPorcentaje();

        BigDecimal p2 = porcentajeService.getPorcentaje();

        assertEquals(new BigDecimal("0.25"), p1);
        assertEquals(new BigDecimal("0.25"), p2);
        verify(porcentajeClient, times(1)).obtenerPorcentaje();
    }

    @Test
    void testFallbackDesdeCache_Exitoso() {

        BigDecimal cachedPorcentaje = new BigDecimal("0.20");
        cacheManager.getCache("porcentaje").put(SimpleKey.EMPTY, cachedPorcentaje);

        doThrow(new RuntimeException("Fallo externo"))
                .when(porcentajeClient).obtenerPorcentaje();

        BigDecimal resultado = calculadoraService.calcular(BigDecimal.ONE, BigDecimal.ONE);

        BigDecimal suma = BigDecimal.ONE.add(BigDecimal.ONE);
        BigDecimal esperado = suma.add(suma.multiply(cachedPorcentaje));
        assertEquals(esperado, resultado);
    }

    @Test
    void testFallbackDesdeCache_SinValor_LanzaExcepcion() {
        cacheManager.getCache("porcentaje").clear();

        doThrow(new RuntimeException("Fallo externo"))
                .when(porcentajeClient).obtenerPorcentaje();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            calculadoraService.calcular(BigDecimal.ONE, BigDecimal.ONE);
        });
        assertEquals("No hay porcentaje en caché y el servicio externo falló", exception.getMessage());
    }

}