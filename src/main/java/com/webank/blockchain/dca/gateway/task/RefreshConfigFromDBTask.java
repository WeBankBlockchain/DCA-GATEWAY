package com.webank.blockchain.dca.gateway.task;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.webank.blockchain.dca.gateway.config.SystemConfig;
import com.webank.blockchain.dca.gateway.config.ThresholdConfig;
import com.webank.blockchain.dca.gateway.mapper.SysConfigMapper;
import com.webank.blockchain.dca.gateway.mapper.ThresholdInfoMapper;
import com.webank.blockchain.dca.gateway.model.SysConfig;
import com.webank.blockchain.dca.gateway.model.ThresholdInfo;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RefreshConfigFromDBTask {

    @Autowired private SystemConfig systemConfig;
    @Autowired private HashMap<String, RateLimiter> rateLimiterMap;
    @Autowired private ThresholdConfig thresholdConfig;
    @Autowired private ThresholdInfoMapper thresholdInfoMapper;
    @Autowired private SysConfigMapper sysConfigMapper;

    private int currentVersion = 0;

    @Scheduled(cron = "${system.taskCron}")
    public void schedule() {
        SysConfig sysConfig = sysConfigMapper.findByConfigName("currentVersion");
        int newVersion = systemConfig.getConfigVersion();
        if (sysConfig != null && StringUtils.isNumeric(sysConfig.getConfigValue())) {
            newVersion = Integer.parseInt(sysConfig.getConfigValue());
        }
        if (newVersion == currentVersion) {
            log.info("no need to schedule task, version is {}", currentVersion);
            return;
        }
        log.info(
                "UpdateConfigTask: it's time to get config from DB, from version: {}, to version {}",
                currentVersion,
                newVersion);
        this.updateThreshold();
        updateServiceLimiter();
        currentVersion = systemConfig.getConfigVersion();
    }

    private void updateThreshold() {
        List<ThresholdInfo> thresholds = thresholdInfoMapper.findAll();
        if (CollectionUtil.isNotEmpty(thresholds)) {
            Map<String, String> map = new HashMap<>();
            for (ThresholdInfo t : thresholds) {
                log.debug("threshold info read {}", JsonUtils.toJson(t));
                if (StringUtils.equalsIgnoreCase(t.getThreshold(), map.get(t.getBusiName()))) {
                    continue;
                }
                map.put(t.getBusiName(), t.getThreshold());
                log.info("{} set to {}", t.getBusiName(), t.getThreshold());
            }
            log.info("threshold info write {}", JsonUtils.toJson(map));
            BeanUtil.copyProperties(map, thresholdConfig);
            log.debug("new report {}", thresholdConfig.getReport());
        }
    }

    private void updateServiceLimiter() {
        List<SysConfig> sysConfigs = sysConfigMapper.findAll();
        if (CollectionUtil.isNotEmpty(sysConfigs)) {
            for (SysConfig t : sysConfigs) {
                if (StringUtils.endsWith(t.getConfigName(), "Limit")) {
                    if (StringUtils.isNumeric(t.getConfigValue())
                            && rateLimiterMap.containsKey(t.getConfigName())) {
                        double d = Double.parseDouble(t.getConfigValue());
                        rateLimiterMap.put(t.getConfigName(), RateLimiter.create(d));
                        log.info("Rate Limit {} set to {}", t.getConfigName(), d);
                    } else {
                        log.error(
                                "OnError: invalid config {}, value {}",
                                t.getConfigName(),
                                t.getConfigValue());
                    }
                }
            }
        }
    }
}
