package com.webank.blockchain.dca.gateway.controller.json;

import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.service.FileService;
import com.webank.blockchain.dca.gateway.service.StoreService;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.UploadByFpsReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class FpsController {
    @Autowired StoreService storeService;
    @Autowired FileService fileService;

    @PostMapping("/dca/uploadByFps")
    public CommonResponse uploadByFps(@Validated @RequestBody UploadByFpsReq uploadByFpsReq)
            throws IOException {
        log.info("uploadByFps request is {}", JsonUtils.toJson(uploadByFpsReq));
        String fileId = uploadByFpsReq.getStoreIdInfo().get(0).getFileId();
        String appId = uploadByFpsReq.getAppId();
        if (StringUtils.isEmpty(fileId) || !StringUtils.contains(fileId, "_")) {
            return CommonResponse.error(ResponseEnum.Invalid_File_ID);
        }
        String collectionId = StringUtils.substringBeforeLast(fileId, "_");
        if (!fileService.checkCollectionID(collectionId, appId)) {
            return CommonResponse.error(ResponseEnum.No_Collection_Id);
        }
        return storeService.storeByFps(
                uploadByFpsReq.getSeqNo(),
                uploadByFpsReq.getStoreIdInfo(),
                uploadByFpsReq.getFileType(),
                appId,
                collectionId,
                uploadByFpsReq.getAlgorithm());
    }
}
