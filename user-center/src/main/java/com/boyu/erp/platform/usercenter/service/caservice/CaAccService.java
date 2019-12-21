package com.boyu.erp.platform.usercenter.service.caservice;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.dept.Department;
import com.boyu.erp.platform.usercenter.entity.dept.DeptAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.dept.DepartmentVo;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.github.pagehelper.PageInfo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 类描述:
 *
 * @Description: 往来账户记账
 * @auther: wz
 * @date: 2019/9/10 11:41
 */
public interface CaAccService {

    //解冻
    void defreeze(CaAcc caAcc, SysUser user) throws Exception;

    //冻结
    void freeze(CaAcc caAcc, SysUser user, Ca ca, BigDecimal debitValue,BigDecimal creditValue,boolean flag) throws Exception;

    //授信
    void accredit(CaAcc caAcc, SysUser user, Ca ca, BigDecimal debitValue,BigDecimal creditValue,boolean flag) throws Exception;

    //取消授信
    void revoke(CaAcc caAcc, SysUser user) throws Exception;

    /**
     * 往来账户中查询往来账户授信明细
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws Exception
     */
    PageInfo<CaAcc> selectAll(CaVo vo, Integer page, Integer size, SysUser sysUser) throws Exception;

}
