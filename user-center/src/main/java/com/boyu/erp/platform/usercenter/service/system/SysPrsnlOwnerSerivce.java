package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrsnlOwner;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 人员属主接口
 * @author: liu yan
 * @create: 2019-05-20 11:36
 */
public interface SysPrsnlOwnerSerivce {
    /**
     * 查询人员属主
     */
    SysPrsnlOwner findById(SysPrsnlOwner prsnlOwner);

    /**
     * 查询封装人员表和owner表集合
     * @author HHe
     * @date 2019/9/30 12:02
     */
    List<OwnerPrsnlModel> packNumAndName2List(List<OwnerPrsnlModel> ownerPrsnlModelList);

}
