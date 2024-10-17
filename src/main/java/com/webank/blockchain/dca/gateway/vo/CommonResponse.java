package com.webank.blockchain.dca.gateway.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private String responseCode = ResponseEnum.Success.getResponseCode();
    private String responseMessage = "success";
    private String debugMessage;
    private String seqNo;
    private T responseData;

    public CommonResponse(
            String responseCode, String responseMessage, String debugMessage, T responseData) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.debugMessage = debugMessage;
        this.responseData = responseData;
    }

    public CommonResponse(String seqNo) {
        this.seqNo = seqNo;
    }

    public static <T> CommonResponse<T> OK() {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        return commonResponse;
    }

    public static <T> CommonResponse<T> OK(CommonRequest request) {
        CommonResponse<T> commonResponse = new CommonResponse<>(request.getSeqNo());
        return commonResponse;
    }

    public static <T> CommonResponse<T> data(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setResponseData(data);
        return commonResponse;
    }

    public static <T> CommonResponse<T> data(T data, CommonRequest request) {
        CommonResponse<T> commonResponse = new CommonResponse<>(request.getSeqNo());
        commonResponse.setResponseData(data);
        return commonResponse;
    }

    public static <T> CommonResponse<T> error(String responseCode, String responseMessage) {
        CommonResponse<T> commonResponse =
                new CommonResponse<>(responseCode, responseMessage, null, null);
        return commonResponse;
    }

    public static <T> CommonResponse<T> error(ResponseEnum res) {
        CommonResponse<T> commonResponse =
                new CommonResponse<>(res.getResponseCode(), res.getMessage(), null, null);
        return commonResponse;
    }

    public static <T> CommonResponse<T> error(ResponseEnum res, String debugMessage) {
        CommonResponse<T> commonResponse =
                new CommonResponse<>(res.getResponseCode(), res.getMessage(), debugMessage, null);
        return commonResponse;
    }

    @JsonIgnore
    public boolean isOK() {
        return responseCode.equals(ResponseEnum.Success.getResponseCode());
    }
}
