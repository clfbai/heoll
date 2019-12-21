package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdAttrDef;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;

import java.util.List;

/**
 * 商品属性定义
 */
public interface ProdAttrDefMapper {

    public List<ProdAttrDef> selectAll(ProdAttrDef record);

    public ProdAttrDef selectByPrimaryKey(ProdAttrDef attrType);

    public int insertSelective(ProdAttrDef record);

    public int updateByPrimaryKeySelective(ProdAttrDef record);

    public int deleteByPrimaryKey(ProdAttrDef attrType);

    /**
     * 查询所有自定义属性 属性类别对应属性名称
     */
    List<ProdAttrDefVo> getAllProdAttrDef();
}