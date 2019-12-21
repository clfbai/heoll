package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.SpnDtl;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnDtlVo;

import java.util.List;
import java.util.Map;

public interface SpnDtlMapper {
    int deleteByPrimaryKey(SpnDtl key);

    int insert(SpnDtl record);

    int insertSelective(SpnDtl record);

    SpnDtl selectByPrimaryKey(SpnDtl key);

    int updateByPrimaryKeySelective(SpnDtl record);

    int updateByPrimaryKey(SpnDtl record);

    /**
     * 批量新增
     * @param list
     * @return
     */
    int insertByList(List<SpnDtlVo> list);

    /**
     * 删除销售价格单删除明细
     * @param unitId
     * @param spnNum
     * @return
     */
    int deleteBySpn(String unitId,String spnNum);

    /**
     * 查询销售价格单明细
     * @param vo
     * @return
     */
    List<SpnDtlVo> selectBySpn(SpnDtlVo vo);

    /**
     * 批量修改
     * @param list
     * @return
     */
    int updateByList(List<SpnDtlVo> list);

    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteByList(List<SpnDtlVo> list);

    /**
     * 添加销售价格单时批量添加
     * @param map
     * @return
     */
    int insertByMap(Map<String,Object> map);
}