package com.boyu.erp.platform.usercenter.service.base;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;

/**
 * 类描述:
 *
 * @Description: 通用弹窗查询接口
 * @auther: CLF
 * @date: 2019/9/23 17:15
 */
public interface BaseService {
    /**
     * 功能描述: 查询通用弹窗方法
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/29 14:52
     */
    Object selectObject(CommonWindowModel model, SysUser user);
}
