package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.model.CollectionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CollectionInfoMapper {

    @Select("SELECT * FROM collection_info WHERE collection_id = #{collectionId}")
    CollectionInfo findByCollectionId(String collectionId);

    @Select(
            "SELECT * FROM collection_info WHERE collection_id = #{collectionId} and app_id = #{appId}")
    CollectionInfo findByCollectionIdAndAppId(String collectionId, String appId);

    @UseTime
    @Insert(
            "INSERT INTO collection_info(collection_id, app_id,collection_name,description) VALUES(#{collectionId}, #{appId}, #{collectionName}, #{description})")
    int save(CollectionInfo collectionInfo);
}
