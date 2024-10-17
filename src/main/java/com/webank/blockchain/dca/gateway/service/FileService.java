package com.webank.blockchain.dca.gateway.service;

import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    /**
     * 文件上传接口
     *
     * @param file
     * @return
     */
    public <T> CommonResponse<T> saveFile(MultipartFile file) throws IOException;

    public boolean checkCollectionID(String collectionId, String appId);

    public <T> CommonResponse<T> checkFileContentBase64(InputStream is);

    public <T> CommonResponse<T> checkFileContentBase64(byte[] bytes);

    public <T> CommonResponse<T> checkFileContent(String filePath);
}
