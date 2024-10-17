package com.webank.blockchain.dca.gateway.mapper;

import com.webank.blockchain.dca.gateway.model.WmFileVector;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WmFileVectorMapper {

    @Select("<script>" +
            "<foreach collection='tables' separator='union all' item='table'>" +
            " select app_id, unique_id from wm_file_vector_#{table} where unique_hash= #{uniqueHash} and algorithm_type = #{algorithmType}" +
            "</foreach> " +
            " </script>")
    @Results({
            @Result(column = "app_id", property = "appId"),
            @Result(column = "unique_id", property = "uniqueId")
    })
    WmFileVector getInfoByHash(@Param("tables")List<Integer> tables,
                                 @Param("uniqueHash") String hash,
                                 @Param("algorithmType") int algorithmType);

}
