package com.webank.blockchain.dca.gateway.utils;

import cn.hutool.json.JSONUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileMetaUtil {

    public static final List<String> metaTags = Lists.newArrayList(
            "Creation Time",
            "Image Height",
            "Image Width",
            "DateTimeOriginal",
            "DateTimeDigitized",
            "Date/Time Original",
            "Date/Time Digitized",
            "Author",
            "Version");

    public static String extractMetaInfo(byte[] bytes){
        List<String> tags = new ArrayList<>();
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(bytes));
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if(metaTags.contains(tag.getTagName())) {
                        tags.add(tag.toString());
                    }
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        tags.add("ERROR:" + error);
                    }
                }
            }
            return JSONUtil.toJsonStr(tags);
        } catch (Exception e) {
            log.warn("Exception extractMeta failed ,reason is ", e);
        } catch (Error error) {
            log.warn("Error extractMeta failed ,reason is ", error);
        }
        return "";
    }
}
