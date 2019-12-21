package com.boyu.erp.platform.usercenter.TPOS.mapper;

import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.WStkoCn;
import org.springframework.stereotype.Repository;

@Repository
public interface WStkoCnMapper {
    int deleteByPrimaryKey(String id);

    int insert(WStkoCn record);

    int insertSelective(WStkoCn record);

    WStkoCn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WStkoCn record);

    int updateByPrimaryKey(WStkoCn record);
}