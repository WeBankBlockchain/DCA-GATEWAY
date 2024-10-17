package com.webank.blockchain.dca.gateway.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.webank.security.file.FileTypeChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Configuration
@Slf4j
public class BeanConfig {

    @PostConstruct
    public void fileChecker() {
        // 只支持这些文件上传, 后缀名必须小写
        ArrayList<String> allowFileTypeArray =
                new ArrayList<String>(Arrays.asList("jpg", "png", "jpg", "jpeg", "gif"));
        // 是否允许上传可执行文件
        Boolean isAllowExecFile = false;
        // 是否允许上传本SDK支持的文件
        Boolean isAllowAllSupportFile = false;
        // 初始化配置
        FileTypeChecker.initConfig(allowFileTypeArray, isAllowExecFile, isAllowAllSupportFile);
    }

    @Bean
    public ThreadLocal<String> seqNoThreadLocal() {
        ThreadLocal<String> seqNoThreadLocal = new ThreadLocal<>();
        return seqNoThreadLocal;
    }

    @Bean
    public Cache<String, Boolean> prepairCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(10)
                .build();
    }
}
