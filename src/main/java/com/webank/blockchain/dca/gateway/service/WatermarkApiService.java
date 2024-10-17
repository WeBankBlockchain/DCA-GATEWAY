package com.webank.blockchain.dca.gateway.service;

import com.webank.blockchain.dca.gateway.aspect.Limit;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.WaterMarkInfo;
import com.webank.blockchain.dca.gateway.vo.WmAuthResponse;

import java.io.IOException;

public interface WatermarkApiService {


    String addWatermark(
            String seqNo,
            String appId,
            String fileName,
            String uniqueId,
            int fileType,
            int algorithm,
            byte[] bytes) throws IOException;


    @Limit(name = "authWaterMarkLimit")
    CommonResponse authByWatermark(String seqNo, String appId, String hash, double threshold, String fileName, byte[] bytes)
            throws Exception;
}
