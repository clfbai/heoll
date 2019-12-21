package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.PknDtl;
import org.springframework.stereotype.Repository;

@Repository
public interface PknDtlMapper {

    int insert(PknDtl record);

    int insertSelective(PknDtl record);

    int updateByPrimaryKeySelective(PknDtl record);

    int updateByPrimaryKey(PknDtl record);
}