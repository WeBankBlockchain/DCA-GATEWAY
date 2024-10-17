package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContentAuthReq extends CommonRequest {
    private MultipartFile fileContent;
    private String fileName;
    private int fileType = 1;
    private String appId;
}
