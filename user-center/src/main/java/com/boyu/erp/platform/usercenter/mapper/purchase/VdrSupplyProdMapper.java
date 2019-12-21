package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.VdrSupplyProd;
import com.boyu.erp.platform.usercenter.model.purchase.VdrSupplyProdModel;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;

import java.util.List;
import java.util.Map;

public interface VdrSupplyProdMapper {
    int deleteByPrimaryKey(VdrSupplyProd key);

    int insert(VdrSupplyProd record);

    int insertSelective(VdrSupplyProd record);

    VdrSupplyProd selectByPrimaryKey(VdrSupplyProd key);

    int updateByPrimaryKeySelective(VdrSupplyProd record);

    int updateByPrimaryKey(VdrSupplyProd record);

    List<VdrSupplyProdVo> selectAll(VdrSupplyProdVo vo);

    int add(VdrSupplyProdModel vo);

    int delete(VdrSupplyProdModel vo);

    VdrSupplyProd selectByVer(Map<String,Object> map);
}