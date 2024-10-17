package com.webank.blockchain.dca.gateway.service;

import com.webank.blockchain.dca.gateway.vo.AddAppReq;
import com.webank.blockchain.dca.gateway.vo.AddCollectionReq;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;

public interface RegisterService {

    public <T> CommonResponse<T> registerApp(AddAppReq addAppReq);

    public <T> CommonResponse<T> registerCollection(AddCollectionReq addCollectionReq);
}
