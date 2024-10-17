package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DelWatermarkReq extends CommonRequest{
    private String uniqueId;
    private String appId;
}
