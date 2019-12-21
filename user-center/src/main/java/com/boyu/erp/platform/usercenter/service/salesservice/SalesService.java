package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.mq.order.OrderAdd;
import com.boyu.erp.platform.usercenter.entity.mq.order.SrcOrderAdd;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

/**
 * @Classname SalesService
 * @Description TODO
 * @Date 2019/11/29 10:06
 * @Created by wz
 */
public interface SalesService {

    /**
     * B2B订单  接收处理  生成-确认-审核
     * @param add
     * @return
     */
    int creatSlc(OrderAdd add, SysUser user) throws Exception;

    /**
     * B2B退销订单  接收处理  生成-确认-审核
     * @param add
     * @param user
     * @return
     * @throws Exception
     */
    int creatSrc(SrcOrderAdd add, SysUser user) throws Exception;
}
