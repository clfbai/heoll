package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.github.pagehelper.PageInfo;

/**
 * @Classname PscService
 * @Description TODO
 * @Date 2019/7/17 21:05
 * @Created wz
 */
public interface PscService {

    /**
     * 采购更新收货中任务数
     * type为1时（加） 为2时（减）
     */
    int updateTaskByP(String pscNum,String type);

    /**
     * 采购更新收货中任务数
     * type为1时（加） 为2时（减）
     */
    int updateTaskByS(String pscNum,String type);
}
