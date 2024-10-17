package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.aspect.UseTime;
import com.webank.blockchain.dca.gateway.model.AppInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AppInfoMapper {

    @Select("SELECT * FROM app_info WHERE app_id = #{appId}")
    AppInfo findByAppId(String appId);

    @UseTime
    @Insert(
            "INSERT INTO app_info(app_id, app_name, description) VALUES(#{appId}, #{appName}, #{description})")
    int save(AppInfo appInfo);
}
