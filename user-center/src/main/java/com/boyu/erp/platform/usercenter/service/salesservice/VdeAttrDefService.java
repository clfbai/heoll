package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.sales.VdeAttrDef;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 采购商属性定义接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface VdeAttrDefService {

    public int deleteByAttrType(VdeAttrDef record) throws ServiceException;;

    public int insert(VdeAttrDef record) throws ServiceException;;

    public int updateByAttrType(VdeAttrDef record) throws ServiceException;;

    public PageInfo<VdeAttrDef> getAllVdeAttrDefList(Integer page, Integer size, VdeAttrDef record);

    /**
     * 采购商中的采购商属性
     * @return
     */
    List<ProdAttrDefVo> findByAll();

}
