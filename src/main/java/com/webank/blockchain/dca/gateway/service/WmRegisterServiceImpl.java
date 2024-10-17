package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.cache.Cache;
import com.webank.blockchain.dca.gateway.mapper.WmAppInfoMapper;
import com.webank.blockchain.dca.gateway.model.AppInfo;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.RegisterAppReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WmRegisterServiceImpl implements WmRegisterService {
    @Autowired
    private WmAppInfoMapper appInfoMapper;
    @Autowired
    private Cache<String, Boolean> cache;

    @Override
    public <T> CommonResponse<T> registerApp(RegisterAppReq addAppReq) {
        AppInfo appInfo = new AppInfo();
        BeanUtil.copyProperties(addAppReq, appInfo, true);
        appInfoMapper.save(appInfo);
        return CommonResponse.OK(addAppReq);
    }

    @Override
    public boolean existAppId(String appId) {
        Boolean exist = cache.getIfPresent(appId);
        if(exist == null || !exist) {
           exist = appInfoMapper.findByAppId(appId) != null;
           cache.put(appId, exist);
           log.info("Query DB, appId {} exist {}", appId, exist);
        }
        return exist;
    }
}
