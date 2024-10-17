package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddAppReq extends CommonRequest {
    private String appId;
    private String appName;
    private String description = "";
}
