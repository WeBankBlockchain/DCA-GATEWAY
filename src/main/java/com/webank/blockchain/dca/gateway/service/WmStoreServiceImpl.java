package com.webank.blockchain.dca.gateway.service;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.config.SystemConfig;
import com.webank.blockchain.dca.gateway.constant.SystemConstant;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.mapper.WmComputeTaskMapper;
import com.webank.blockchain.dca.gateway.mapper.WmFileVectorMapper;
import com.webank.blockchain.dca.gateway.model.WmFileVector;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Service
public class WmStoreServiceImpl implements WmStoreService {
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private HttpService httpService;
    @Autowired
    private FileService fileService;
    @Autowired
    private WmComputeTaskMapper wmComputeTaskMapper;
    @Autowired
    private WmFileVectorMapper wmFileVectorMapper;

    @UseTime
    @Override
    public <T> CommonResponse<T> store(byte[] bytes, String seqNo, String uniqueId, int fileType,
                                       String appId, String hash, int algorithm, String fileHash) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("seqNo", seqNo);
        paramMap.put("uniqueId", uniqueId);
        paramMap.put("fileType", fileType);
        paramMap.put("appId", appId);
        paramMap.put("uniqueHash", hash);
        paramMap.put("algorithm", algorithm);
        paramMap.put("fileHash",fileHash);
        HttpResponse resp =
                    HttpUtil.createPost(systemConfig.getWmStoreUrl() + SystemConstant.UPLOAD)
                            .form(paramMap)
                            .form("file", bytes, hash)
                            .execute();
        log.info("Store return: code [{}]  body [{}]", resp.getStatus(), resp.body());
        if (resp.isOk()) {
            CommonResponse<T> cr = JsonUtils.fromJson(resp.body(), CommonResponse.class);
            return cr;
        } else {
            return CommonResponse.error(ResponseEnum.Request_Error);
        }
    }

    @Override
    public boolean existUniqueId(String uniqueId, String appId, int algType){
        return wmComputeTaskMapper.existUniqueId(uniqueId,appId, algType) != 0;
    }

    @Override
    public boolean existUniqueHash(String uniqueHash, String appId, int algType){
       WmFileVector fv = wmFileVectorMapper.getInfoByHash(Lists.newArrayList(0,1,2,3), uniqueHash, algType);
       if(fv == null) {
           return false;
       }
        return StringUtils.equalsIgnoreCase(fv.getAppId(), appId);
    }

    @Override
    public String queryIdByWatermarkFileHash(String appId, String fileHash){
        return wmComputeTaskMapper.queryIdByWatermarkFileHash(appId,fileHash);
    }

    @Override
    public String queryHashByUniqueId(String appId, String uniqueId){
        return wmComputeTaskMapper.queryHashByUniqueId(appId,uniqueId);
    }

    @Override
    public <T> CommonResponse<T> delWatermark(String seqNo, String uniqueId, String appId){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("seqNo", seqNo);
        paramMap.put("uniqueId", uniqueId);
        paramMap.put("appId", appId);
        HttpResponse resp =
                HttpUtil.createPost(systemConfig.getWmStoreUrl() + SystemConstant.DEL)
                        .form(paramMap)
                        .execute();
        log.info("Store return: code [{}]  body [{}]", resp.getStatus(), resp.body());
        if (resp.isOk()) {
            CommonResponse<T> cr = JsonUtils.fromJson(resp.body(), CommonResponse.class);
            return cr;
        } else {
            return CommonResponse.error(ResponseEnum.Request_Error);
        }
    }
}
