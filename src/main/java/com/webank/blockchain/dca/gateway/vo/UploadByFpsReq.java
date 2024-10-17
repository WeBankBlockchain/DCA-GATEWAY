package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadByFpsReq extends CommonRequest {
    @Length(min = 5, message = "appId长度错误")
    private String appId;

    private List<StoreIdInfo> storeIdInfo;
    private int fileType = 0;
    private int algorithm = 0;
}
