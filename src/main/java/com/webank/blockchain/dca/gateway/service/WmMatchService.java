package com.webank.blockchain.dca.gateway.service;

import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.WmMatchRes;

import java.io.IOException;

public interface WmMatchService {

    CommonResponse<WmMatchRes> auth(
            byte[] file,
            String uniqueHash,
            String seqNo,
            double threshold
            ) throws IOException;

}
