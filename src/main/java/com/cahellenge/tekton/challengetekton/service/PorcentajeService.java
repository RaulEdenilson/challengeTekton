package com.cahellenge.tekton.challengetekton.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class PorcentajeService {

    private static final Logger log = LoggerFactory.getLogger(PorcentajeService.class);
    private final PorcentajeClient client;

    public PorcentajeService(PorcentajeClient client) {
        this.client = client;
    }

    @Cacheable("porcentaje")
    public BigDecimal getPorcentaje() {
        log.info("Cache miss: obteniendo porcentaje del servicio externo");
        BigDecimal p = client.obtenerPorcentaje();
        log.info("Porcentaje obtenido: {}", p);
        return p;
    }
}