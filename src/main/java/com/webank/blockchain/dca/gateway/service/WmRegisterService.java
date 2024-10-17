package com.webank.blockchain.dca.gateway.service;

import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.RegisterAppReq;

public interface WmRegisterService {

    <T> CommonResponse<T> registerApp(RegisterAppReq addAppReq);

    boolean existAppId(String appId);
}
