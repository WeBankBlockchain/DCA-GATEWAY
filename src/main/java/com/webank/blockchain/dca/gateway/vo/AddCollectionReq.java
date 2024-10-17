package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddCollectionReq extends CommonRequest {
    private String appId;
    private String collectionId;
    private String collectionName = "";
    private String description = "";
}
