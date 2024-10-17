package com.webank.blockchain.dca.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "system")
public class SystemConfig {
    private String tmpDir;
    private String storeUrl;
    private String wmStoreUrl;

    private String waterMarkUrl;

    private String matchUrl;
    private String wmMatchUrl;

    private int configVersion;
    private String taskCron;
    // 设置支持最大上传的文件，这里是1024*1024*2=2M
    private long maxFileSize = 2097152L;
}
