package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.webank.blockchain.dca.gateway.aspect.Limit;
import com.webank.blockchain.dca.gateway.config.SystemConfig;
import com.webank.blockchain.dca.gateway.config.ThresholdConfig;
import com.webank.blockchain.dca.gateway.constant.SystemConstant;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.MatchResponseData;
import com.webank.blockchain.dca.gateway.vo.ScanResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {
    @Autowired private SystemConfig systemConfig;
    @Autowired private HttpService httpService;
    @Autowired private ThresholdConfig thresholdConfig;

    @Limit(name = "authLimit")
    @Override
    public CommonResponse<MatchResponseData> auth(
            InputStreamResource file,
            String fileName,
            String seqNo,
            String appId,
            double threshold,
            int size) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", file);
        paramMap.put("fileName", fileName);
        paramMap.put("seqNo", seqNo);
        paramMap.put("appId", appId);
        paramMap.put("fileType", 0);
        paramMap.put("algorithm", 0);
        paramMap.put("size", size);
        paramMap.put("threshold", threshold);
        HttpResponse resp =
                HttpUtil.createPost(systemConfig.getMatchUrl() + SystemConstant.MATCH_FILE)
                        .form(paramMap)
                        .execute();
        if (resp.isOk()) {
            log.info("{}", resp.body());
            CommonResponse<MatchResponseData> cr =
                    JsonUtils.fromJson(resp.body(), CommonResponse.class, MatchResponseData.class);
            return cr;
        } else {
            return CommonResponse.error(ResponseEnum.Request_Error);
        }
    }

    @Limit(name = "reportLimit")
    @Override
    public CommonResponse<ScanResponseData> report(
            InputStreamResource file, String fileName, String seqNo, int pageNo, int pageSize) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", file);
        paramMap.put("fileName", fileName);
        paramMap.put("seqNo", seqNo);
        paramMap.put("fileType", 0);
        paramMap.put("appId", "");
        paramMap.put("algorithm", 0);
        paramMap.put("pageNo", pageNo);
        paramMap.put("pageSize", pageSize);
        paramMap.put("threshold", thresholdConfig.getReport());
        HttpResponse resp =
                HttpUtil.createPost(systemConfig.getMatchUrl() + SystemConstant.SCAN)
                        .form(paramMap)
                        .execute();
        if (resp.isOk()) {
            CommonResponse<ScanResponseData> cr =
                    JsonUtils.fromJson(resp.body(), CommonResponse.class, ScanResponseData.class);
            return cr;
        } else {
            return CommonResponse.error(ResponseEnum.Request_Error);
        }
    }

    @Limit(name = "reviewLimit")
    @Override
    public CommonResponse<ScanResponseData> review(
            InputStreamResource file, String fileName, String seqNo, int pageNo, int pageSize) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", file);
        paramMap.put("fileName", fileName);
        paramMap.put("seqNo", seqNo);
        paramMap.put("fileType", 0);
        paramMap.put("appId", "");
        paramMap.put("algorithm", 0);
        paramMap.put("pageNo", pageNo);
        paramMap.put("pageSize", pageSize);
        paramMap.put("threshold", thresholdConfig.getReview());
        HttpResponse resp =
                HttpUtil.createPost(systemConfig.getMatchUrl() + SystemConstant.SCAN)
                        .form(paramMap)
                        .execute();
        if (resp.isOk()) {
            CommonResponse<ScanResponseData> cr =
                    JsonUtils.fromJson(resp.body(), CommonResponse.class, ScanResponseData.class);
            return cr;
        } else {
            return CommonResponse.error(ResponseEnum.Request_Error);
        }
    }
}
