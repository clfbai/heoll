package com.boyu.erp.platform.usercenter.mapper.purchase;


import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;

import java.util.List;

/**
 * 商品属性定义
 */
public interface VdrAttrDefMapper {

    public List<VdrAttrDef> selectALL(VdrAttrDef vdrAttrDef);

    public int insertVdrAttrDef(VdrAttrDef vdrAttrDef);

    public int updateVdrAttrDef(VdrAttrDef vdrAttrDef);

    public int deleteVdrAttrDef(VdrAttrDef vdrAttrDef);

    List<ProdAttrDefVo> getAll();

}