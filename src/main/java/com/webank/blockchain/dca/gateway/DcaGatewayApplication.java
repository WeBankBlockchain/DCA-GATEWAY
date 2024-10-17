package com.webank.blockchain.dca.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class DcaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcaGatewayApplication.class, args);
    }
}
