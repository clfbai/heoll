package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.UnitBgModel;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 组织品牌分组
 * @author: liu yan
 * @create: 2019-05-23 15:39
 */
public interface UnitBgService {

    List<Bg> unitBgAll(UnitBgModel mode);

    void unitBgUpdate(UnitBgModel bgMode, SysUser user);
}
