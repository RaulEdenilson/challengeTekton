package com.cahellenge.tekton.challengetekton.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager("porcentaje");
        manager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(30, TimeUnit.MINUTES)
        );
        return manager;
    }
}