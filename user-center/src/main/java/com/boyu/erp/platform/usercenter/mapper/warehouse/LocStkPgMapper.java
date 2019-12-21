package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.LocStkPg;

public interface LocStkPgMapper {
    int deleteLocStkPg(LocStkPg key);

    /**
     * 删除仓库时货位未决库存
     */
    int deleteLocStkPgWareh(Long warehId);

    int insertLocStkPg(LocStkPg record);

    LocStkPg selectLocStkPg(LocStkPg key);

    int updateLocStkPg(LocStkPg record);

}