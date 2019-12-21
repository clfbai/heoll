package com.boyu.erp.platform.usercenter.service.priceservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.price.XplHVo;
import com.github.pagehelper.PageInfo;

/**
 * @program: XplHService
 * @description 价格单历史接口
 * @author: wz
 * @create: 2019-8-30 9:43
 */
public interface XplHService {

    /**
     * 价格单历史查询
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<XplHVo> selectAll(XplHVo vo, Integer page, Integer size, SysUser sysUser);

}
