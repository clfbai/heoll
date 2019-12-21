package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.partner.PsxTypeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * @Classname PsxTypeService
 * @Description TODO
 * @Date 2019/6/18 20:03
 * @Created wz
 */
public interface PsxTypeService {

    /**
     * 购销申请类别下拉
     */
    List<PsxType> optionGet();

    /**
     * 购销申请查询
     * @param page
     * @param size
     * @param psxType
     * @return
     */
    PageInfo<PsxTypeVo> selectAll(Integer page, Integer size, PsxType psxType);

    /**
     * 添加购销申请类别
     * @param psxType
     * @param user
     * @return
     */
    int insertPsxType(PsxType psxType, SysUser user);

    /**
     * 修改购销申请类别
     * @return
     */
    int updatePsxType(PsxType psxType, SysUser user) throws Exception;

    /**
     * 删除购销申请类别
     * @param psxType
     * @return
     */
    int deletePsxType(PsxType psxType);
}
