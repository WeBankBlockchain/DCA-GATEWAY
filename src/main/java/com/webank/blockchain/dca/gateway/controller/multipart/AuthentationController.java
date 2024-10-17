package com.webank.blockchain.dca.gateway.controller.multipart;

import cn.hutool.core.io.resource.InputStreamResource;
import com.webank.blockchain.dca.gateway.config.ThresholdConfig;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.service.FileService;
import com.webank.blockchain.dca.gateway.service.MatchService;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.MatchResponseData;
import com.webank.blockchain.dca.gateway.vo.ScanResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class AuthentationController {
    @Autowired private FileService fileService;
    @Autowired private MatchService matchService;
    @Autowired private ThresholdConfig thresholdConfig;

    @PostMapping("/dca/auth")
    public CommonResponse<MatchResponseData> auth(
            @RequestParam String seqNo,
            @RequestParam(required = false, defaultValue = "0") int fileType,
            @RequestParam String appId,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") double threshold,
            @RequestPart MultipartFile file)
            throws Exception {
        log.info(
                "auth request is seqNo: {} fileType: {} appId: {} collectionId: {}",
                seqNo,
                fileType,
                appId);
        if (size < 1 || size > 100) {
            return CommonResponse.error(ResponseEnum.Invalid_Request_Param);
        }
        if (Double.isNaN(threshold) || Double.compare(threshold, 1) == 1
                || Double.compare(threshold, 0) <= 0) {
            threshold = thresholdConfig.getAuth();
        }
        CommonResponse<MatchResponseData> r =
                fileService.checkFileContentBase64(file.getInputStream());
        if (!r.isOK()) {
            return r;
        }
        return matchService.auth(
                new InputStreamResource(file.getInputStream(), file.getName()),
                file.getOriginalFilename(),
                seqNo,
                appId,
                threshold,
                size);
    }

    @PostMapping("/dca/report")
    public CommonResponse report(
            @RequestParam String seqNo,
            @RequestParam(required = false, defaultValue = "0") int fileType,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestPart MultipartFile file)
            throws Exception {
        log.info(
                "report request is seqNo: {} fileType: {}  pageNo: {} pageSize: {}",
                seqNo,
                fileType,
                pageNo,
                pageSize);
        if (pageNo < 1 || pageNo > 20 || pageSize < 1 || pageSize > 100) {
            return CommonResponse.error(ResponseEnum.Invalid_Request_Param);
        }
        CommonResponse r = fileService.checkFileContentBase64(file.getInputStream());
        if (!r.isOK()) {
            return r;
        }
        CommonResponse<ScanResponseData> res =
                matchService.report(
                        new InputStreamResource(file.getInputStream(), file.getName()),
                        file.getOriginalFilename(),
                        seqNo,
                        pageNo,
                        pageSize);
        return res;
    }

    @PostMapping("/dca/review")
    public CommonResponse review(
            @RequestParam String seqNo,
            @RequestParam(required = false, defaultValue = "0") int fileType,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestPart MultipartFile file)
            throws Exception {
        log.info(
                "review request is seqNo: {} fileType: {}  pageNo: {} pageSize: {}",
                seqNo,
                fileType,
                pageNo,
                pageSize);
        if (pageNo < 1 || pageNo > 20 || pageSize < 1 || pageSize > 100) {
            return CommonResponse.error(ResponseEnum.Invalid_Request_Param);
        }
        CommonResponse r = fileService.checkFileContentBase64(file.getInputStream());
        if (!r.isOK()) {
            return r;
        }
        CommonResponse<ScanResponseData> res =
                matchService.review(
                        new InputStreamResource(file.getInputStream(), file.getName()),
                        file.getOriginalFilename(),
                        seqNo,
                        pageNo,
                        pageSize);
        return res;
    }
}
