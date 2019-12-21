package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.LocBst;

public interface LocBstMapper {
    int deleteByPrimaryKey(LocBst key);

    /**
     * 删除仓库时删除货位库存
     */
    int deleteLocBstWareh(Long warehId);

    int insertSelective(LocBst record);

    LocBst selectByPrimaryKey(LocBst key);

    int updateByPrimaryKeySelective(LocBst record);


}