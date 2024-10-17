package com.webank.blockchain.dca.gateway.controller.multipart;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.webank.blockchain.dca.gateway.aspect.Record;
import com.webank.blockchain.dca.gateway.config.ThresholdConfig;
import com.webank.blockchain.dca.gateway.enums.FileType;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.enums.TXType;
import com.webank.blockchain.dca.gateway.model.Context;
import com.webank.blockchain.dca.gateway.service.FileService;
import com.webank.blockchain.dca.gateway.service.WatermarkApiService;
import com.webank.blockchain.dca.gateway.service.WmRegisterService;
import com.webank.blockchain.dca.gateway.utils.FileMetaUtil;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@RestController
@Slf4j
@Validated
@Api(tags = "鉴真接口")
public class WmAuthentationController {

    @Autowired
    private FileService fileService;
    @Autowired
    private WatermarkApiService watermarkApiService;
    @Autowired
    private ThresholdConfig thresholdConfig;
    @Autowired
    private WmRegisterService registerService;

    @Record(type = "auth")
    @PostMapping("/dca/authByWatermark")
    public CommonResponse authByWatermark(
            @RequestParam String seqNo,
            @RequestParam(required = false, defaultValue = "0") @Range(max = 0,message = "fileType not in range") int fileType,
            @RequestParam(required = false, defaultValue = "0") double threshold,
            @RequestParam @NotBlank(message = "appId can not blank") String appId,
            @RequestParam @NotBlank(message = "hash can not blank") String hash,
            @RequestPart MultipartFile file)
            throws Exception {
        log.info(
                "watermark auth request is seqno: {} threshold: {} fileType: {} appId: {} uniqueId: {}",
                seqNo,
                threshold,
                fileType,
                appId,
                hash);

        Context context = Context.getContext();
        context.setAppId(appId);
        context.setSeqNo(seqNo);
        context.setTxType(TXType.AUTH.getCode());
        context.setUniqueId(hash);

        if(!registerService.existAppId(appId)){
            context.setResponseCode(ResponseEnum.AppId_Not_Exist.getResponseCode());
            return CommonResponse.error(ResponseEnum.AppId_Not_Exist);
        }
        if (Double.isNaN(threshold) || Double.compare(threshold, 1) == 1
                || Double.compare(threshold, 0) <= 0) {
            threshold = thresholdConfig.getWmAuth();
        }
        context.setThreshold(threshold);

        String fileName = file.getOriginalFilename();
        obtainFileType(fileName,context);
        byte[] bytes =IoUtil.readBytes(file.getInputStream());
        context.setFileSize(bytes.length / 1024.0);
        log.info("file name is {} and file size is {}", fileName, String.format("%.2f KB",context.getFileSize()));
        CommonResponse fileCheckRes = fileService.checkFileContentBase64(bytes);
        if (!fileCheckRes.isOK()) {
            context.setResponseCode(ResponseEnum.Invalid_File_Format.getResponseCode());
            return fileCheckRes;
        }
        context.setFileMeta(FileMetaUtil.extractMetaInfo(bytes));
        log.info("[ {} ] imageInfo = {}",
                seqNo,
                context.getFileMeta());
        CommonResponse res =  watermarkApiService.authByWatermark(seqNo, appId, hash, threshold, fileName, bytes);
        return res;
    }

    private void obtainFileType(String fileName, Context context) {
        if (fileName == null){
            return;
        }
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.info("fileExtension = {} ,filename = {}" ,fileExtension,fileName);
        if ("png".equalsIgnoreCase(fileExtension)){
            context.setFileType(FileType.PNG.getCode());
        }else if("jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension)){
            context.setFileType(FileType.JPG.getCode());
        }else {
            context.setFileType(FileType.UNKNOWN.getCode());
        }
    }


}
