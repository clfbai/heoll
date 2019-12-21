package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitClsf;

import java.util.List;

public interface SysUnitClsfSerivcer {

    int addUnitClsf(SysUnitClsf unitClsf);

    int updateUnitClsf(SysUnitClsf unitClsf);
    /**
     * 查询并返回含有组织Id的组织Id
     * @author HHe
     * @date 2019/10/12 9:55
     */
    List<Long> queryHaveInUnitIds(String unitType,Long ownerId,List<Long> unitIds);
}
