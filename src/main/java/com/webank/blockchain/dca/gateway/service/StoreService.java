package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.io.resource.InputStreamResource;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.StoreIdInfo;

import java.io.IOException;
import java.util.List;

public interface StoreService {

    public <T> CommonResponse<T> store(
            InputStreamResource fileContent,
            String seqNo,
            String fileId,
            int fileType,
            String appId,
            String collectionId,
            int algorithm)
            throws IOException;

    public <T> CommonResponse<T> store(
            String url,
            String seqNo,
            String fileId,
            int fileType,
            String appId,
            String collectionId,
            int algorithm)
            throws IOException;

    public <T> CommonResponse<T> storeByFps(
            String seqNo,
            List<StoreIdInfo> storeIdInfos,
            int fileType,
            String appId,
            String collectionId,
            int algorithm)
            throws IOException;
}
