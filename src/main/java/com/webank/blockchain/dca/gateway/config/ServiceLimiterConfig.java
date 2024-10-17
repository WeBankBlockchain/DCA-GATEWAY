package com.webank.blockchain.dca.gateway.config;

import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Configuration
@ConfigurationProperties(prefix = "servicelimit")
@Slf4j
public class ServiceLimiterConfig {

    private ConcurrentHashMap<String, Double> limitMap = new ConcurrentHashMap<>();

    @Bean
    public HashMap<String, RateLimiter> getRateLimiterMap() {
        HashMap<String, RateLimiter> map = new HashMap<>();
        limitMap.forEach(
                (k, v) -> {
                    map.put(k, RateLimiter.create(v));
                    log.info("RateLimiter {}, init {}", k, v);
                });
        return map;
    }
}
