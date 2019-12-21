package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 调配单类别接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface RgoTypeService {

    public int deleteByRgoType(RgoType rgoType) throws ServiceException;

    public int insert(RgoType rgoType) throws ServiceException;

    public int updateByRgoType(RgoType rgoType) throws ServiceException;

    public PageInfo<RgoType> getRgoTypeList(Integer page, Integer size, RgoType rgoType) throws ServiceException;

    /**
     * 调配单下拉框
     */
    List<PurKeyValue> optionGet();

    /**
     * 查询选择调配单类别后自动填充数据
     * @param rgoType
     * @return
     */
    RgoType selectByRgoAuto(String rgoType);
}
