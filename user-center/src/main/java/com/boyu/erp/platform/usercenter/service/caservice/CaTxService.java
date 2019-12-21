package com.boyu.erp.platform.usercenter.service.caservice;

import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.basic.CaTx;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

import java.text.ParseException;

/**
 * 类描述:
 *
 * @Description: 往来账户事务
 * @auther: wz
 * @date: 2019/9/10 11:41
 */
public interface CaTxService {

    /**
     * 单据审核时添加数据
     * @param caTx
     * @return
     */
    int insertByBill(CaTx caTx, SysUser user) throws Exception;
}
