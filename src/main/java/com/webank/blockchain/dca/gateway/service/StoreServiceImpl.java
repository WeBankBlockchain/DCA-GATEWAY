package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.webank.blockchain.dca.gateway.aspect.Limit;
import com.webank.blockchain.dca.gateway.config.SystemConfig;
import com.webank.blockchain.dca.gateway.constant.SystemConstant;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.StoreIdInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired private SystemConfig systemConfig;
    @Autowired private HttpService httpService;
    @Autowired private FileService fileService;

    @Limit(name = "storeLimit")
    @Override
    public <T> CommonResponse<T> store(
            InputStreamResource file,
            String seqNo,
            String fileId,
            int fileType,
            String appId,
            String collectionId,
            int algorithm)
            throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", file);
        paramMap.put("seqNo", seqNo);
        paramMap.put("fileId", fileId);
        paramMap.put("fileType", fileType);
        paramMap.put("appId", appId);
        paramMap.put("collectionId", collectionId);
        paramMap.put("algorithm", algorithm);
        HttpResponse resp =
                HttpUtil.createPost(systemConfig.getStoreUrl() + SystemConstant.UPLOAD)
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

    @Override
    public <T> CommonResponse<T> store(
            String url,
            String seqNo,
            String fileId,
            int fileType,
            String appId,
            String collectionId,
            int algorithm)
            throws IOException {
        String newName = System.currentTimeMillis() + Thread.currentThread().getName();
        File newFile = new File(systemConfig.getTmpDir(), newName);
        InputStreamResource inputStreamResource =
                new InputStreamResource(new FileInputStream(newFile), newFile.getName());
        long size = HttpUtil.downloadFile(url, newFile);
        if (size == 0) {
            return CommonResponse.error(
                    ResponseEnum.Download_File_Error.getResponseCode(),
                    ResponseEnum.Download_File_Error.getMessage());
        }
        CommonResponse<T> r = fileService.checkFileContent(newFile.getAbsolutePath());
        if (!r.isOK()) {
            FileUtil.del(newFile);
            return r;
        }
        CommonResponse<T> response =
                store(inputStreamResource, seqNo, fileId, fileType, appId, collectionId, algorithm);
        FileUtil.del(newFile);
        return response;
    }

    @Override
    public <T> CommonResponse<T> storeByFps(
            String seqNo,
            List<StoreIdInfo> storeIdInfos,
            int fileType,
            String appId,
            String collectionId,
            int algorithm)
            throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("seqNo", seqNo);
        paramMap.put("appId", appId);
        paramMap.put("collectionId", collectionId);
        paramMap.put("algorithm", algorithm);
        paramMap.put("storeIdInfoList", storeIdInfos);
        paramMap.put("fileType", fileType);
        HttpResponse resp =
                HttpUtil.createPost(systemConfig.getStoreUrl() + SystemConstant.UPLOAD_BY_FPS)
                        .body(JsonUtils.toJson(paramMap))
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
