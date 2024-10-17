package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.webank.blockchain.dca.gateway.aspect.Limit;
import com.webank.blockchain.dca.gateway.enums.FailReason;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.model.Context;
import com.webank.blockchain.dca.gateway.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class WatermarkApiServiceImpl implements WatermarkApiService{

    @Autowired
    private WmStoreService storeService;
    @Autowired
    private WaterMarkService waterMarkService;
    @Autowired
    private WmMatchService matchService;

    @Limit(name = "addWaterMarkLimit")
    @Override
    public String addWatermark(
            String seqNo,
            String appId,
            String fileName,
            String uniqueId,
            int fileType,
            int algorithm,
            byte[] bytes) throws IOException {
        String fileHash = HexUtil.encodeHexStr(DigestUtil.sha1(bytes));
        String uniqueIdExist = storeService.queryIdByWatermarkFileHash(appId,fileHash);
        if (uniqueIdExist != null) {
            throw new IllegalArgumentException(ResponseEnum.FILE_HASH_EXIST.getResponseCode());
        }
        CommonResponse storeRes = storeService.store(
                bytes,
                seqNo,
                uniqueId,
                fileType,
                appId,
                fileHash,
                algorithm,
                fileHash);
        if(!storeRes.isOK()){
            throw new RuntimeException(ResponseEnum.Store_ERROR.getResponseCode());
        }
        return fileHash;
    }

    @Limit(name = "authWaterMarkLimit")
    @Override
    public CommonResponse authByWatermark(String seqNo, String appId, String uniqueId, double threshold, String fileName, byte[] bytes)
            throws Exception {
        Context context = Context.getContext();
        String fileHash = DigestUtil.sha1Hex(bytes);
        context.setFileHash(fileHash);
        log.info("watermark file hash is {}", fileHash);
        String existedHash = storeService.queryHashByUniqueId(appId, uniqueId);
        if(existedHash == null || StrUtil.isBlank(existedHash)){
            context.setPositive(false);
            context.setFailReason(FailReason.VEC_NOT_EXIST.getCode());
            return CommonResponse.data(
                    new WmAuthResponse(false,null,"file is not exist in system"));
        }
        if (StrUtil.equalsIgnoreCase(existedHash, fileHash)) {
            log.info("file hash match succeed");
            context.setReturnUniqueId(uniqueId);
            context.setPositive(true);
            context.setSimilarity(1);
            return CommonResponse.data(
                    new WmAuthResponse(true, uniqueId, null));
        }
        CommonResponse<WmMatchRes> response = matchService.auth(
                bytes,
                uniqueId,
                seqNo,
                threshold);
        log.info("matchService.auth finish, seqNo is {}, result is {}",
                seqNo, JSONUtil.toJsonStr(response));
        if (!response.isOK()) {
            context.setResponseCode(ResponseEnum.Match_Error.getResponseCode());
            return response;
        }
        WmMatchRes wmMatchRes = response.getResponseData();
        context.setPositive(wmMatchRes.isPositive());
        context.setFailReason(FailReason.parse(wmMatchRes.getFailReason()).getCode());
        context.setReturnUniqueId(wmMatchRes.getUniqueId());
        context.setSimilarity(Float.parseFloat(wmMatchRes.getSimilarity()));
        return CommonResponse.data(
                new WmAuthResponse(wmMatchRes.isPositive(), wmMatchRes.getUniqueId(),wmMatchRes.getFailReason()));

    }

}
