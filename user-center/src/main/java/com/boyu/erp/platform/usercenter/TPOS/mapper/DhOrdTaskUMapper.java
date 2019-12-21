package com.boyu.erp.platform.usercenter.TPOS.mapper;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTaskU;
import org.springframework.stereotype.Repository;

@Repository
public interface DhOrdTaskUMapper {
    int insert(DhOrdTaskU record);

    int insertSelective(DhOrdTaskU record);

    int updateByPrimaryKeySelective(DhOrdTaskU record);

    int updateByPrimaryKey(DhOrdTaskU record);
}