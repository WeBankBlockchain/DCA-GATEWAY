package com.webank.blockchain.dca.gateway.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaterMarkInfo {

    private String hash;

    private String wmFileName;

    private byte[] wmFileBytes;
}
