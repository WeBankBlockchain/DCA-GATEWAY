package com.webank.blockchain.dca.gateway.controller.multipart;

import cn.hutool.core.io.resource.InputStreamResource;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.service.FileService;
import com.webank.blockchain.dca.gateway.service.StoreService;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class UploadController {
    @Autowired FileService fileService;
    @Autowired StoreService storeService;

    @PostMapping("/dca/upload")
    public <T> CommonResponse<T> upload(
            @RequestParam String seqNo,
            @RequestParam(required = false, defaultValue = "0") int fileType,
            @RequestParam(required = false) String fileUrl,
            @RequestParam String fileId,
            @RequestParam String appId,
            @RequestParam(required = false, defaultValue = "0") int algorithm,
            @RequestPart MultipartFile file)
            throws IOException {
        if (algorithm != 0) {
            return CommonResponse.error(
                    ResponseEnum.Invalid_Request_Param, "not supported algorithm");
        }
        if (StringUtils.isEmpty(fileId) || !StringUtils.contains(fileId, "_")) {
            return CommonResponse.error(ResponseEnum.Invalid_File_ID);
        }
        String collectionId = StringUtils.substringBeforeLast(fileId, "_");
        if (!fileService.checkCollectionID(collectionId, appId)) {
            return CommonResponse.error(ResponseEnum.No_Collection_Id);
        }
        log.info("report request is seqNo: {}  fileId: {} collectionId: {} ", seqNo, appId, fileId);
        if (!file.isEmpty()) {
            CommonResponse<T> r = fileService.checkFileContentBase64(file.getInputStream());
            if (!r.isOK()) {
                return r;
            }
            return storeService.store(
                    new InputStreamResource(file.getInputStream(), file.getName()),
                    seqNo,
                    fileId,
                    fileType,
                    appId,
                    collectionId,
                    algorithm);

        } else {
            if (StringUtils.isEmpty(fileUrl)) {
                return CommonResponse.error(
                        ResponseEnum.Invalid_File_Uri.getResponseCode(),
                        ResponseEnum.Invalid_File_Uri.getMessage());
            }
            return storeService.store(
                    fileUrl, seqNo, fileId, fileType, appId, collectionId, algorithm);
        }
    }
}
