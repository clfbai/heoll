package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.entity.sales.VdeAttrDef;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;

import java.util.List;

public interface VdeAttrDefMapper {
    int deleteByAttrType(VdeAttrDef record);

    int insert(VdeAttrDef record);

    int updateByAttrType(VdeAttrDef record);

    List<VdeAttrDef> getAllVdeAttrDefList(VdeAttrDef record);

    List<ProdAttrDefVo> getAll();

}