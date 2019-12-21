package com.boyu.erp.platform.usercenter.service.caservice;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.basic.CaFrz;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;

/**
 * 类描述:
 *
 * @Description: 往来账户冻结明细
 * @auther: wz
 * @date: 2019/10/6 11:41
 */
public interface CaFrzService {

    /**
     * 查询往来账户中冻结明细
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws Exception
     */
    PageInfo<CaFrz> selectAll(CaVo vo, Integer page, Integer size, SysUser sysUser) throws Exception;

}
