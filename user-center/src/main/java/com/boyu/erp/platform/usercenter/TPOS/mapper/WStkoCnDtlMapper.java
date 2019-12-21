package com.boyu.erp.platform.usercenter.TPOS.mapper;

import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.WStkoCnDtl;
import org.springframework.stereotype.Repository;

@Repository
public interface WStkoCnDtlMapper {
    int insert(WStkoCnDtl record);

    int insertSelective(WStkoCnDtl record);

    int updateByPrimaryKeySelective(WStkoCnDtl record);

    int updateByPrimaryKey(WStkoCnDtl record);
}