package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PucTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * @Classname PucTypeService
 * @Description TODO
 * @Date 2019/6/19 11:55
 * @Created wz
 */
public interface PucTypeService {


    /**
     * 分页显示采购合同类别
     *
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<PucTypeVo> selectAll(Integer page, Integer size, PucType pucType) throws ServiceException;

    /**
     * 增加采购合同类别
     *
     * @param pucType
     * @return
     * @throws ServiceException
     */
    public int insertPucType(PucType pucType, SysUser user) throws Exception;

    /**
     * 修改采购合同类别
     *
     * @param pucType
     * @return
     * @throws ServiceException
     */
    public int updatePucType(PucType pucType, SysUser sysUser) throws Exception;

    /**
     * 删除采购合同类别
     *
     * @param pucType
     * @return
     * @throws ServiceException
     */
    public int deletePucType(PucType pucType, SysUser sysUser) throws ServiceException;

    /**
     * 采购合同下拉框
     */
    List<PurKeyValue> optionGet();

    /**
     * 通过选取的类别查询出相关数据
     */
    PscAutoVo selectByPscAuto(String pucType);
}