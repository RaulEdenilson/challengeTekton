package com.cahellenge.tekton.challengetekton.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

class CacheConfigTest {

    private final CacheConfig cacheConfig = new CacheConfig();

    @Test
    void testCacheManagerNotNull() {
        // Act
        CaffeineCacheManager cacheManager = cacheConfig.cacheManager();

        // Assert
        assertNotNull(cacheManager, "CacheManager no debe ser nulo");
    }

    @Test
    void testPorcentajeCacheExists() {
        // Arrange
        CaffeineCacheManager cacheManager = cacheConfig.cacheManager();

        // Act
        Cache porcentajeCache = cacheManager.getCache("porcentaje");

        // Assert
        assertNotNull(porcentajeCache, "El cache 'porcentaje' debe existir");
    }
}