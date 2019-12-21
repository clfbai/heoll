package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehBstPg;

public interface WarehBstPgMapper {
    int deleteWarehBstPg(WarehBstPg key);

    /**
     * 删除仓库时仓库装箱未决库存
     */
    int deleteWarehBstPgWareh(Long warehId);

    int insertWarehBstPg(WarehBstPg record);

    WarehBstPg selectWarehBstPg(WarehBstPg key);

    int updateWarehBstPg(WarehBstPg record);

}