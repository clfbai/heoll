package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.entity.shop.Dps;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

public interface DpsServicer {
    PageInfo<Dps> getDps(Integer page, Integer size, Dps dps, SysUser user);

    int addDps(Dps dps);

    int deleteDps(Dps dps);

    int updateDps(Dps dps);
}
