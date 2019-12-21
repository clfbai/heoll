package com.boyu.erp.platform.usercenter.mapper.Price;

import com.boyu.erp.platform.usercenter.entity.Price.PpnDtl;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;

import java.util.List;
import java.util.Map;

public interface PpnDtlMapper {
    int deleteByPrimaryKey(PpnDtl key);

    int insert(PpnDtl record);

    int insertSelective(PpnDtl record);

    PpnDtl selectByPrimaryKey(PpnDtl key);

    int updateByPrimaryKeySelective(PpnDtl record);

    int updateByPrimaryKey(PpnDtl record);

    /**
     * 删除采购价格单删除明细
     * @param unitId
     * @param ppnNum
     * @return
     */
    int deleteByPpn(String unitId,String ppnNum);

    /**
     * 查询采购价格单明细
     * @param vo
     * @return
     */
    List<PpnDtlVo> selectByPpn(PpnDtlVo vo);

    /**
     * 批量新增
     * @param list
     * @return
     */
    int insertByList(List<PpnDtlVo> list);

    /**
     * 批量修改
     * @param list
     * @return
     */
    int updateByList(List<PpnDtlVo> list);

    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteByList(List<PpnDtlVo> list);

    /**
     * 添加销售价格单时批量添加
     * @param map
     * @return
     */
    int insertByMap(Map<String,Object> map);
}