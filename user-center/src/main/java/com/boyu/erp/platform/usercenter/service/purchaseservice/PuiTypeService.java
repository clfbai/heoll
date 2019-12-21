package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PuiType;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PuiTypeVo;
import com.github.pagehelper.PageInfo;

/**
 * @Classname PuiTypeService
 * @Description TODO
 * @Date 2019/6/18 14:16
 * @Created wz
 */
public interface PuiTypeService {

    /**
     * 分页显示采购意向类别
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<PuiTypeVo> selectAll(Integer page, Integer size, PuiType puiType) throws ServiceException;

    /**
     * 增加采购意向类别
     * @param puiType
     * @return
     * @throws ServiceException
     */
    public int insertPuiType(PuiType puiType) throws ServiceException;

    /**
     * 修改采购意向类别
     * @param puiType
     * @return
     * @throws ServiceException
     */
    public int updatePuiType(PuiType puiType)throws ServiceException;

    /**
     * 删除采购意向类别
     * @param puiType
     * @return
     * @throws ServiceException
     */
    public int deletePuiType(PuiType puiType) throws ServiceException;
    
}
