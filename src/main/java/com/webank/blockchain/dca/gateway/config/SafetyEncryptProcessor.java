package com.webank.blockchain.dca.gateway.config;

import com.webank.blockchain.dca.gateway.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class SafetyEncryptProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(
            ConfigurableEnvironment environment, SpringApplication application) {
        HashMap<String, Object> map = new HashMap<>();
        String prv = "";
        String pub = "";
        String dbPasswordStr = "";
        for (PropertySource<?> ps : environment.getPropertySources()) {
            if (ps instanceof OriginTrackedMapPropertySource) {
                OriginTrackedMapPropertySource source = (OriginTrackedMapPropertySource) ps;
                for (String name : source.getPropertyNames()) {
                    Object value = source.getProperty(name);
                    log.info(name + " ======= " + value);
                    if (value instanceof String) {
                        String str = String.valueOf(value);
                        if (StringUtils.equalsIgnoreCase("app.publicStr", name)) {
                            pub = str;
                        }
                        if (StringUtils.equalsIgnoreCase("app.privateStr", name)) {
                            prv = str;
                        }
                        if (StringUtils.equalsIgnoreCase("spring.datasource.password", name)) {
                            dbPasswordStr = str;
                        }
                    }
                }
            }
        }

        String dbPassword =
                StringUtils.length(dbPasswordStr) > 32
                        ? SecurityUtil.decrypt(dbPasswordStr, prv, pub)
                        : dbPasswordStr;
        map.put("spring.datasource.password", dbPassword);

        // 将处理的数据放入环境变量，并处于第一优先级上 (这里一定要注意，覆盖其他配置)
        if (!map.isEmpty()) {
            log.info("replace map: " + map.size());
            environment.getPropertySources().addFirst(new MapPropertySource("prefixer", map));
        }
    }
}
