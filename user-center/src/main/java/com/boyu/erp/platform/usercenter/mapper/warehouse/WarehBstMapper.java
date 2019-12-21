package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehBst;

public interface WarehBstMapper {
    int deleteWarehBst(WarehBst key);
    /**
     * 删除仓库时删除仓库装箱库存
     * */
    int deleteWarehBstWareh(Long warehId);

    int insertWarehBst(WarehBst record);

    WarehBst selectWarehBst(WarehBst key);

    int updateWarehBst(WarehBst record);


}