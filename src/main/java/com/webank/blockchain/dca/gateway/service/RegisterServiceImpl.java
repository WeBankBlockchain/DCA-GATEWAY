package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.bean.BeanUtil;
import com.webank.blockchain.dca.gateway.mapper.AppInfoMapper;
import com.webank.blockchain.dca.gateway.mapper.CollectionInfoMapper;
import com.webank.blockchain.dca.gateway.model.AppInfo;
import com.webank.blockchain.dca.gateway.model.CollectionInfo;
import com.webank.blockchain.dca.gateway.vo.AddAppReq;
import com.webank.blockchain.dca.gateway.vo.AddCollectionReq;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired private AppInfoMapper appInfoMapper;
    @Autowired private CollectionInfoMapper collectionInfoMapper;

    @Override
    public <T> CommonResponse<T> registerApp(AddAppReq addAppReq) {
        AppInfo appInfo = new AppInfo();
        BeanUtil.copyProperties(addAppReq, appInfo, true);
        appInfoMapper.save(appInfo);
        return CommonResponse.OK(addAppReq);
    }

    @Override
    public <T> CommonResponse<T> registerCollection(AddCollectionReq addCollectionReq) {
        CollectionInfo collectionInfo = new CollectionInfo();
        BeanUtil.copyProperties(addCollectionReq, collectionInfo, true);
        collectionInfoMapper.save(collectionInfo);
        return CommonResponse.OK(addCollectionReq);
    }
}
