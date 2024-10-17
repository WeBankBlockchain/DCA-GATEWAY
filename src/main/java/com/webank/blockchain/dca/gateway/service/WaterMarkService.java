package com.webank.blockchain.dca.gateway.service;

import com.webank.blockchain.dca.gateway.vo.AuthWaterMarkRes;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.WaterMarkInfo;

import java.io.IOException;

public interface WaterMarkService {

    WaterMarkInfo addWaterMark(byte[] bytes, String seqNo, String appId, String uniqueId) throws IOException;

    CommonResponse<AuthWaterMarkRes> authWaterMark(byte[] bytes, String seqNo) throws IOException;
}
