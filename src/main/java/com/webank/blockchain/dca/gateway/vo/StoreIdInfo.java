package com.webank.blockchain.dca.gateway.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StoreIdInfo {
    @NotBlank private String fileId;
    @NotBlank private String fpsFileId;
    @NotBlank private String fpsFileHash;
}
