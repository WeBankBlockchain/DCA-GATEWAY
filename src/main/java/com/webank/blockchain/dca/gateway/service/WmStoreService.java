package com.webank.blockchain.dca.gateway.service;

import com.webank.blockchain.dca.gateway.vo.CommonResponse;

import java.io.IOException;

public interface WmStoreService {

    <T> CommonResponse<T> store(
            byte[] bytes,
            String seqNo,
            String uniqueId,
            int fileType,
            String appId,
            String hash,
            int algorithm,
            String fileHash)
            throws IOException;

    boolean existUniqueId(String uniqueId, String appId, int algType);

    boolean existUniqueHash(String hash, String appId, int algType);

    String queryIdByWatermarkFileHash(String appId, String fileHash);

    String queryHashByUniqueId(String appId, String uniqueId);

    <T> CommonResponse<T> delWatermark(String seqNo, String uniqueId, String appId);
}
