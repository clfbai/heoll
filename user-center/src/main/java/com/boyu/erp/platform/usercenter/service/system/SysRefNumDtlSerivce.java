package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 编号明细接口
 * @author: clf
 * @create: 2019-05-15 09:56
 */
public interface SysRefNumDtlSerivce {

    SysRefNumDtl findById(SysRefNumDtl sysRefNumDtl);

    PageInfo<SysRefNumDtl> getPage(Integer page, Integer size, SysRefNumDtl sysRefNumDtl);

    int updateRefNumDtl(SysRefNumDtl sysRefNumDtl);

    int addRefNumDtl(SysRefNumDtl sysRefNumDtl);

    List<SysRefNumDtl> getAll(SysRefNumDtl refNumdtl, SysUser user);

    /**
     * 获得最大编号 和增量
     */
    SysRefNumDtl getLastStep(SysRefNumDtl sysRefNumDtl);


    /**
     * 根据 主键字段、增量、和最大编号生成当前唯一Id (目前仅支持生成number)
     */
    Long creatId(String flied);

    /**
     * 查询最新编号
     */
    Long selectLum(SysRefNumDtl flied);
    /**
     * 根据编号Id和组织Id查询编号Dtl
     * @author HHe
     * @date 2019/9/19 19:55
     */
    SysRefNumDtl querySysRefDtlByNumIdAndUnitId(SysRefNumDtl sysRefNumDtl);
    /**
     * 自增最大编号并且返回
     * @author HHe
     * @date 2019/9/20 10:51
     */
    Long updateAutoIncrementLastNum(SysRefNumDtl sysRefNumDtl, Long stepSize);
}
