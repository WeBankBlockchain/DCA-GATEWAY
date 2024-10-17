package com.webank.blockchain.dca.gateway.controller.multipart;

import cn.hutool.core.io.IoUtil;
import com.webank.blockchain.dca.gateway.aspect.Record;
import com.webank.blockchain.dca.gateway.constant.SystemConstant;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.enums.TXType;
import com.webank.blockchain.dca.gateway.model.Context;
import com.webank.blockchain.dca.gateway.service.FileService;
import com.webank.blockchain.dca.gateway.service.WatermarkApiService;
import com.webank.blockchain.dca.gateway.service.WmRegisterService;
import com.webank.blockchain.dca.gateway.service.WmStoreService;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.WaterMarkInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@Slf4j
@Validated
@Api(tags = "加水印接口")
public class WmUploadController {

    @Autowired
    FileService fileService;
    @Autowired
    WmStoreService storeService;
    @Autowired
    WatermarkApiService watermarkApiService;
    @Autowired
    WmRegisterService registerService;

    @Record(type = "add")
    @PostMapping("/dca/addWatermark")
    public <T> CommonResponse<String> addWatermark(
            @RequestParam String seqNo,
            @RequestParam(required = false, defaultValue = "0") @Range(max = 0,message = "fileType not in range") int fileType,
            @RequestParam @NotBlank(message = "uniqueId can not blank") String uniqueId,
            @RequestParam @NotBlank(message = "appId can not blank") String appId,
            @RequestParam(required = false, defaultValue = "0") @Range(max = 0,message = "algorithm not in range")  int algorithm,
            @RequestPart MultipartFile file)
            throws IOException {

        Context context = Context.getContext();
        context.setAppId(appId);
        context.setSeqNo(seqNo);
        context.setUniqueId(uniqueId);
        context.setTxType(TXType.ADD.getCode());
        context.setAlgorithm(algorithm);

        log.info(
                "watermark auth addWaterMark is seqno: {} uniqueId: {} fileType: {} appId: {} algorithm: {}",
                seqNo,
                uniqueId,
                fileType,
                appId,
                algorithm);
        if(!registerService.existAppId(appId)){
            throw new IllegalArgumentException(ResponseEnum.AppId_Not_Exist.getResponseCode());
        }
        if (storeService.existUniqueId(uniqueId,appId, algorithm)){
            throw new IllegalArgumentException(ResponseEnum.Exist_Unique_ID.getResponseCode());
        }
        String fileName = file.getName();
        byte[] bytes = IoUtil.readBytes(file.getInputStream());
        log.info("file name is {} and file size is {}", fileName, String.format("%.2f KB", bytes.length / 1024.0));
        CommonResponse<T> r = fileService.checkFileContentBase64(bytes);
        if (!r.isOK()) {
            throw new IllegalArgumentException(ResponseEnum.Invalid_File_Type.getResponseCode());
        }
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        int width = image.getWidth();
        int height = image.getHeight();
        log.info("image width: {}, height: {}", width, height);
        if (width * height < SystemConstant.PIXELS_LIMIT){
            throw new IllegalArgumentException(ResponseEnum.Image_Not_Support.getResponseCode());
        }
        String hash = watermarkApiService.addWatermark(seqNo, appId, fileName,
                uniqueId, fileType, algorithm, bytes);
        return CommonResponse.data(hash);
    }


}
