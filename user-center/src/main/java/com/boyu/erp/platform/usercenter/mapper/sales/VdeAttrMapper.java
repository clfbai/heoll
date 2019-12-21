package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.VdeAttr;

import java.util.List;
import java.util.Map;

public interface VdeAttrMapper {
    int deleteByPrimaryKey(VdeAttr key);

    int insert(VdeAttr record);

    int insertSelective(VdeAttr record);

    VdeAttr selectByPrimaryKey(VdeAttr key);

    int updateByPrimaryKeySelective(VdeAttr record);

    int updateByPrimaryKey(VdeAttr record);

    /**
     * 查询采购商中采购商属性
     * @param ma
     * @return
     */
    List<Map<String, Object>> vdeAttrList(Map<String, Object> ma);
}