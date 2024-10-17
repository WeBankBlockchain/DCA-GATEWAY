package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.io.resource.InputStreamResource;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.MatchResponseData;
import com.webank.blockchain.dca.gateway.vo.ScanResponseData;

public interface MatchService {

    public CommonResponse<MatchResponseData> auth(
            InputStreamResource file,
            String fileName,
            String seqNo,
            String appId,
            double threshold,
            int size);

    public CommonResponse<ScanResponseData> report(
            InputStreamResource file, String fileName, String seqNo, int pageNo, int pageSize);

    public CommonResponse<ScanResponseData> review(
            InputStreamResource file, String fileName, String seqNo, int pageNo, int pageSize);
}
