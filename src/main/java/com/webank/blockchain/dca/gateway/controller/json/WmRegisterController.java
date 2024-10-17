package com.webank.blockchain.dca.gateway.controller.json;

import com.webank.blockchain.dca.gateway.service.WmRegisterService;
import com.webank.blockchain.dca.gateway.utils.JsonUtils;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.blockchain.dca.gateway.vo.RegisterAppReq;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "app注册")
public class WmRegisterController {

    @Autowired
    WmRegisterService registerService;

    @PostMapping("/dca/registerApp")
    public <T> CommonResponse<T> registerApp(@RequestBody RegisterAppReq registerAppReq) {
        log.info("registerApp request is {}", JsonUtils.toJson(registerAppReq));
        return registerService.registerApp(registerAppReq);
    }
}
