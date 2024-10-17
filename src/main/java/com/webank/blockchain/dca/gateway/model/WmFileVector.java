package com.webank.blockchain.dca.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WmFileVector {

    private long pkId;

    private String uniqueId;

    private int algorithmType;

    private String appId;

}
