package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehUnitOptionVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description: 仓库相关下拉
 * @auther: CLF
 * @date: 2019/7/16 16:29
 */
public interface WarehOptionSerivce {

    PageInfo<WarehUnitOptionVo> getPageinfo(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user);

    /**
     * 功能描述: 选择组织后查询当前组织的仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 17:11
     */
    PageInfo<WarehUnitOptionVo> getOwnerIdOption(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user);

    /**
     * 公司下拉查询
     */
    PageInfo<WarehUnitOptionVo> getCompanyinfo(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user);

    /**
     * 发货方弹窗查询
     */
    PageInfo<WarehUnitOptionVo> getDelivOption(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user);

    List<OptionVo> getOption(Long warehId);
}
