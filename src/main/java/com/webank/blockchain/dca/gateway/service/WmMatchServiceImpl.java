package com.webank.blockchain.dca.gateway.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.config.SystemConfig;
import com.webank.blockchain.dca.gateway.constant.SystemConstant;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.WmMatchRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@Slf4j
public class WmMatchServiceImpl implements WmMatchService{

    @Autowired
    SystemConfig systemConfig;

    @UseTime
    @Override
    public CommonResponse<WmMatchRes> auth(byte[] bytes, String uniqueHash, String seqNo, double threshold) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("uniqueHash", uniqueHash);
        paramMap.put("seqNo", seqNo);
        paramMap.put("fileType", 0);
        paramMap.put("algorithm", 0);
        paramMap.put("threshold", threshold);
        HttpResponse resp =
                HttpRequest.post(systemConfig.getWmMatchUrl() + SystemConstant.AUTH)
                        .form(paramMap)
                        .form("file", bytes, uniqueHash)
                        .execute();
        if (resp.isOk()) {
            return JsonUtils.fromJson(resp.body(), CommonResponse.class, WmMatchRes.class);
        } else {
            log.error("match request error: {}", resp.getStatus());
            return CommonResponse.error(ResponseEnum.Match_Error);
        }
    }
}
