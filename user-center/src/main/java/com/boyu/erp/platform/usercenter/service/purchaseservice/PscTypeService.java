package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PscType;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.partner.PscTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * @Classname PscTypeService
 * @Description TODO
 * @Date 2019/6/19 12:05
 * @Created wz
 */
public interface PscTypeService {

    /**
     * 购销合同类别下拉
     */
    List<PurKeyValue> optionGet();

    /**
     * 购销合同查询
     * @param page
     * @param size
     * @param pscType
     * @return
     */
    PageInfo<PscTypeVo> selectAll(Integer page, Integer size, PscType pscType);

    /**
     * 添加购销合同类别
     * @return
     */
    int insertPscType(PscType pscType, SysUser user);

    /**
     * 修改购销合同类别
     * @param pscType
     * @param user
     * @return
     */
    int updatePscType(PscType pscType, SysUser user) throws Exception;

    /**
     * 删除购销合同类别
     * @param pscType
     * @return
     */
    int deletePscType(PscType pscType);
}
