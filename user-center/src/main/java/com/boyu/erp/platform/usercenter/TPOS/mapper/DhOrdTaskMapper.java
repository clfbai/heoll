package com.boyu.erp.platform.usercenter.TPOS.mapper;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTask;
import org.springframework.stereotype.Repository;

@Repository
public interface DhOrdTaskMapper {
    int insert(DhOrdTask record);

    int insertSelective(DhOrdTask record);

    int updateByPrimaryKeySelective(DhOrdTask record);

    int updateByPrimaryKey(DhOrdTask record);
}