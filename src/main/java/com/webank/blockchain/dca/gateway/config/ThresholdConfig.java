package com.webank.blockchain.dca.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "threshold")
public class ThresholdConfig {
    //一对多对比
    private volatile double report = 0.6;
    private volatile double auth = 0.9999;
    private volatile double review = 0.9;

    //一对一水印对比
    private volatile double wmAuth = 0.9999;

}
