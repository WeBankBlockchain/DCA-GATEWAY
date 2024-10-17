package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;

import java.util.List;

@Data
public class ScanResponseData {
    private int totalCount;
    private List<FileInfo> files;
}
