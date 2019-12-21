package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Pkn;
import org.springframework.stereotype.Repository;

@Repository
public interface PknMapper {

    int insert(Pkn record);

    int insertSelective(Pkn record);

    int updateByPrimaryKeySelective(Pkn record);

    int updateByPrimaryKey(Pkn record);
}