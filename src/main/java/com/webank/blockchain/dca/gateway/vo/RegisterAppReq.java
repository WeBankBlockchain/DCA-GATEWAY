package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterAppReq extends CommonRequest{
    private String appId;
    private String appName;
    private String description = "";
}
