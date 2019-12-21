package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * @Classname PrcTypeService
 * @Description TODO
 * @Date 2019/6/20 10:04
 * @Created wz
 */
public interface PrcTypeService {


    /**
     * 分页显示退购合同类别
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<PrcTypeVo> selectAll(Integer page, Integer size, PrcType prcType) throws ServiceException;

    /**
     * 增加退购合同类别
     * @param prcType
     * @return
     * @throws ServiceException
     */
    public int insertPrcType(PrcType prcType, SysUser user) throws Exception;

    /**
     * 修改退购合同类别
     * @param prcType
     * @return
     * @throws ServiceException
     */
    public int updatePrcType(PrcType prcType, SysUser user)throws Exception;

    /**
     * 删除退购合同类别
     * @param prcType
     * @return
     * @throws ServiceException
     */
    public int deletePrcType(PrcType prcType, SysUser user) throws ServiceException;

    /**
     * 退购合同下拉框
     */
    List<PurKeyValue> optionGet();

    /**
     * 通过选取的类别查询出相关数据
     */
    RtcAutoVo selectByPrcAuto(String prcType);
}
