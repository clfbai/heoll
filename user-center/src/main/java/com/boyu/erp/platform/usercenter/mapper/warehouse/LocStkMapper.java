package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.LocStk;

public interface LocStkMapper {
    int deleteLocStk(LocStk key);
     /**
      * 删除仓库时删除货位库存表数据
      * */
    int deleteLocStkWareh(Long warehId);

    int insertLocStk(LocStk record);

    LocStk selectLocStk(LocStk key);

    int updateLocStk(LocStk record);


}