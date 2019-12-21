package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PpnScp;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PnScpVo;

import java.util.List;
import java.util.Map;

public interface PpnScpMapper {
    int deleteByPrimaryKey(PpnScp key);

    int insert(PpnScp record);

    int insertSelective(PpnScp record);

    //采购中走采购价格单改变数据
    PnScpVo selectByPrice(Long unitId, Long venderId, Long prodClsId);

    /**
     * 删除采购价格单时删除范围
     * @param unitId
     * @param ppnNum
     * @return
     */
    int deleteByPpn(String unitId,String ppnNum);

    List<PpnScpVo> selectByPpn(PpnScpVo VO);

    /**
     * 批量新增
     * @return
     */
    int insertByList(List<PpnScpVo> list);

    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteByList(List<PpnScpVo> list);

    /**
     * 添加销售价格单时批量添加
     * @param map
     * @return
     */
    int insertByMap(Map<String,Object> map);

}