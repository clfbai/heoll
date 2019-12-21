package com.boyu.erp.platform.usercenter.service.purchaseservice;


import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Classname BgService
 * @Description TODO
 * @Date 2019/5/17 16:13
 * @Created wz
 */
public interface VdrAttrDefService {

    /**
     * 分页显示供应商属性
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<VdrAttrDef> selectAll(Integer page, Integer size, VdrAttrDef vdrAttrDef) throws ServiceException;

    /**
     * 增加供应商属性
     * @param vdrAttrDef
     * @return
     * @throws ServiceException
     */
    public int insertVdrAttrDef(VdrAttrDef vdrAttrDef) throws ServiceException;

    /**
     * 修改供应商属性
     * @param vdrAttrDef
     * @return
     * @throws ServiceException
     */
    public int updateVdrAttrDef(VdrAttrDef vdrAttrDef)throws ServiceException;

    /**
     * 删除供应商属性
     * @param vdrAttrDef
     * @return
     * @throws ServiceException
     */
    public int deleteVdrAttrDef(VdrAttrDef vdrAttrDef) throws ServiceException;

    /**
     * 供应商中的供应商属性
     * @return
     */
    List<ProdAttrDefVo> findByAll();
}
