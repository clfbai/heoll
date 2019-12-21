package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttr;

import java.util.*;

public interface VdrAttrMapper {
    int deleteByPrimaryKey(VdrAttr key);

    int insert(VdrAttr record);

    int insertSelective(VdrAttr record);

    VdrAttr selectByPrimaryKey(VdrAttr key);

    int updateByPrimaryKeySelective(VdrAttr record);

    int updateByPrimaryKey(VdrAttr record);

    int insertByVender( List<VdrAttr> list);

    int updateByVender( List<VdrAttr> list);

    List<Map<String, Object>> vdrAttrList(Map<String, Object> ma);
}