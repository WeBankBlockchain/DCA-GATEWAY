package com.webank.blockchain.dca.gateway.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import com.webank.blockchain.dca.gateway.config.SystemConfig;
import com.webank.blockchain.dca.gateway.enums.ResponseEnum;
import com.webank.blockchain.dca.gateway.mapper.CollectionInfoMapper;
import com.webank.blockchain.dca.gateway.model.CollectionInfo;
import com.webank.blockchain.dca.gateway.vo.CommonResponse;
import com.webank.security.file.FileTypeChecker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired private CollectionInfoMapper collectionInfoMapper;
    @Autowired private SystemConfig systemConfig;

    @Override
    public CommonResponse<File> saveFile(MultipartFile file) throws IOException {
        // 获取要上传文件的名称
        String fileName = file.getOriginalFilename();
        log.info("File original file name request {}", fileName);
        log.info(" file name request {}", file.getName());

        // 如果名称为空，返回一个文件名为空的错误
        if (StringUtils.isEmpty(fileName)) {
            return CommonResponse.error(
                    ResponseEnum.Invalid_File_Format.getResponseCode(),
                    ResponseEnum.Invalid_File_Format.getMessage());
        }
        // 如果文件超过最大值，返回超出可上传最大值的错误
        if (file.getSize() > systemConfig.getMaxFileSize()) {
            return CommonResponse.error(
                    ResponseEnum.Invalid_File_Size.getResponseCode(),
                    ResponseEnum.Invalid_File_Size.getMessage());
        }
        // 获取到后缀名
        String suffixName =
                fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        // 文件的保存重新按照时间戳命名
        String newName = System.currentTimeMillis() + Thread.currentThread().getName() + suffixName;
        File newFile = new File(systemConfig.getTmpDir(), newName);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        try {
            // 文件写入
            file.transferTo(newFile);
        } catch (IOException e) {
            throw e;
        }
        log.info("newFile.getAbsolutePath():{}", newFile.getAbsolutePath());
        if (!FileTypeChecker.checkFileTypeValid(newFile.getAbsolutePath())) {
            FileUtil.del(newFile);
            return CommonResponse.error(ResponseEnum.Invalid_File_Format);
        }
        return CommonResponse.data(newFile);
    }

    @Override
    public boolean checkCollectionID(String collectionId, String appId) {
        CollectionInfo collectionInfo =
                collectionInfoMapper.findByCollectionIdAndAppId(collectionId, appId);
        if (collectionInfo == null) {
            return false;
        }
        return true;
    }

    @Override
    public <T> CommonResponse<T> checkFileContentBase64(InputStream is) {
        String base64 = Base64.encode(is);
        if (!FileTypeChecker.checkBase64EncodedFileContentValid(base64)) {
            return CommonResponse.error(ResponseEnum.Invalid_File_Format);
        }
        return CommonResponse.OK();
    }

    @Override
    public <T> CommonResponse<T> checkFileContentBase64(byte[] bytes) {
        String base64 = Base64.encode(bytes);
        if (!FileTypeChecker.checkBase64EncodedFileContentValid(base64)) {
            return CommonResponse.error(ResponseEnum.Invalid_File_Format);
        }
        return CommonResponse.OK();
    }

    @Override
    public <T> CommonResponse<T> checkFileContent(String filePath) {
        if (!FileTypeChecker.checkFileTypeValid(filePath)) {
            return CommonResponse.error(ResponseEnum.Invalid_File_Format);
        }
        return CommonResponse.OK();
    }
}
