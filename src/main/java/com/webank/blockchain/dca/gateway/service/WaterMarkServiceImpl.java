package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.config.SystemConfig;
import com.webank.blockchain.dca.gateway.constant.SystemConstant;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.AuthWaterMarkRes;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.WaterMarkInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
public class WaterMarkServiceImpl implements WaterMarkService{
    @Autowired
    private SystemConfig systemConfig;

    @UseTime
    @Override
    public WaterMarkInfo addWaterMark(byte[] bytes, String seqNo, String appId, String uniqueId) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("seqNo", seqNo);
        paramMap.put("uniqueId", uniqueId);
        paramMap.put("appId", appId);
        HttpResponse resp =
                HttpRequest.post(systemConfig.getWaterMarkUrl() + SystemConstant.ADD_WATERMARK)
                        .form(paramMap)
                        .form("file", bytes, UUID.randomUUID().toString())
                        .execute();
        log.info("watermark return: code [{}] ", resp.getStatus());
        if (resp.isOk()) {
            String wmFileName = resp.header("wmFilename");
            if (wmFileName == null) {
                throw new RuntimeException(ResponseEnum.Add_Watermark_ERROR.getResponseCode());
            }
            InputStream inputStream = resp.sync().bodyStream();
            byte[] fileBytes = IoUtil.readBytes(inputStream);
            if(fileBytes.length > SystemConstant.FILE_LIMIT) {
                throw new IllegalArgumentException(ResponseEnum.WM_FILE_SIZE_OVER.getResponseCode());
            }
            return new WaterMarkInfo(wmFileName.split("\\.")[0], wmFileName,fileBytes);
        } else {
            throw new RuntimeException(ResponseEnum.Request_Error.getResponseCode());
        }
    }

    @UseTime
    @Override
    public CommonResponse<AuthWaterMarkRes> authWaterMark(byte[] bytes, String seqNo) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("seqNo", seqNo);
        HttpResponse resp =
                HttpRequest.post(systemConfig.getWaterMarkUrl() + SystemConstant.AUTH_WATERMARK)
                        .form(paramMap)
                        .form("file", bytes, UUID.randomUUID().toString())
                        .execute();
        if (resp.isOk()) {
            return JsonUtils.fromJson(resp.body(), CommonResponse.class, AuthWaterMarkRes.class);
        } else {
            log.error("watermark http error, status: {}", resp.getStatus());
            return CommonResponse.error(ResponseEnum.Auth_Watermark_ERROR);
        }
    }
}
