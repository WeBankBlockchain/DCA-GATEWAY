package com.webank.blockchain.dca.gateway.vo;

import lombok.Data;

import java.util.List;

@Data
public class MatchRes {
    List<FileInfo> fileInfos;
}
