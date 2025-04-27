package com.cahellenge.tekton.challengetekton.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CalculadoraService {

    private static final Logger log = LoggerFactory.getLogger(CalculadoraService.class);
    private final PorcentajeService porcentajeService;
    private final CacheManager cacheManager;

    public CalculadoraService(PorcentajeService porcentajeService,
                              CacheManager cacheManager) {
        this.porcentajeService = porcentajeService;
        this.cacheManager = cacheManager;
    }

    public BigDecimal calcular(BigDecimal num1, BigDecimal num2) {
        log.info("Iniciando cálculo: num1={}, num2={}", num1, num2);
        BigDecimal suma = num1.add(num2);
        BigDecimal porcentaje;
        try {
            porcentaje = porcentajeService.getPorcentaje();
            log.info("Usando porcentaje: {}", porcentaje);
        } catch (Exception ex) {
            log.warn("Fallo servicio externo, intentando fallback desde cache: {}", ex.getMessage());
            Cache cache = cacheManager.getCache("porcentaje");
            if (cache != null) {
                BigDecimal cached = cache.get(SimpleKey.EMPTY, BigDecimal.class);
                if (cached != null) {
                    porcentaje = cached;
                    log.info("Fallback exitoso, porcentaje cacheado: {}", cached);
                } else {
                    log.error("No hay valor cacheado disponible para fallback");
                    throw new IllegalStateException("No hay porcentaje en caché y el servicio externo falló");
                }
            } else {
                log.error("El caché 'porcentaje' no está disponible");
                throw new IllegalStateException("El caché 'porcentaje' no está disponible");
            }
        }
        BigDecimal resultado = suma.add(suma.multiply(porcentaje));
        log.info("Resultado final del cálculo: {}", resultado);
        return resultado;
    }
}