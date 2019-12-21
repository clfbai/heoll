package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdClsAttr;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsAttrLineVo;

import java.util.List;
import java.util.Map;

public interface ProdClsAttrMapper {
    int deleteByProdClsAttr(ProdClsAttr key);

    int insertProdClsAttr(ProdClsAttr record);

    int insertProdClsAttrSelective(ProdClsAttr record);

    ProdClsAttr selectByProdClsAttr(ProdClsAttr key);

    int updateByProdClsAttrSelective(ProdClsAttr record);


    /**
     * @description: 行转列集合
     * @author: CLF
     */
    List<ProdClsAttrLineVo> getRoeAndLine(ProdClsWithBLOBs prodClsId);

    /**
     * @description: 行转列集合 参数 List
     * @author: CLF
     */
    List<Map<String, Object>> getRoeAndLineFroe(List<String> list);

    /**
     * @description: 行转列集合 参数 Map  （多种类型参数)
     * @author: CLF
     */
    List<Map<String, Object>> getRoeAndLineMap(Map<String, Object> map);
}