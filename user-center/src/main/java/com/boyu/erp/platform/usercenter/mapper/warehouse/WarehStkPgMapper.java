package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkPg;

import java.util.List;
import java.util.Map;

public interface WarehStkPgMapper {
    int deleteByPrimaryKey(WarehStkPg key);

    /**
     * 删除仓库时删除仓库未决库存
     */
    int deleteWarehId(Long key);

    /**
     * 查询仓库未决库存
     */
    List<WarehStkPg> selectByWareh(WarehStkPg w);

    int insertSelective(WarehStkPg record);

    WarehStkPg selectByPrimaryKey(WarehStkPg key);

    int updateByPrimaryKeySelective(WarehStkPg record);


    int selectCountWareh(Long warehId);

    /**
     * 合同审核时添加未决库存
     * @param map
     * @return
     */
    int insertByDtl(Map<String,Object> map);

    /**
     * 发货时负数添加未决库存
     * @param map
     * @return
     */
    int updateByNegDtl(Map<String,Object> map);

    /**
     * 采购合同反审时删除未决库存
     * @param map
     * @return
     */
    int deleteByDtl(Map<String,Object> map);

    /**
     * 采购单调用采购合同分配接口时修改未决
     * @param map
     * @return
     */
    int updateByAdmeasure(Map<String,Object> map);

    /**
     * 供货方发货减少库存
     * @param map
     * @return
     */
    int reduceStkPg(Map<String,Object> map);

    /**
     * 供货方发货增加库存
     * @param map
     * @return
     */
    int increaseStkPg(Map<String,Object> map);

    /**
     * 供货方发货删除库存
     * @param map
     * @return
     */
    int deleteStkPg(Map<String,Object> map);
}