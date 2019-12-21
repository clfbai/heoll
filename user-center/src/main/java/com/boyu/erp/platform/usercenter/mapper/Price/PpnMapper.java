package com.boyu.erp.platform.usercenter.mapper.Price;

import com.boyu.erp.platform.usercenter.entity.Price.Ppn;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;

import java.util.List;

public interface PpnMapper {
    int deleteByPrimaryKey(Ppn key);

    int insert(Ppn record);

    int insertSelective(PpnVo vo);

    Ppn selectByPrimaryKey(Ppn key);

    int updateByPrimaryKeySelective(PpnVo record);

    int updateByState(PpnVo vo);

    int updateByPrimaryKey(Ppn record);

    //系统管理员查询
    List<PpnVo> selectALL(PpnVo vo);
    //组织查询
    List<PpnVo> selectByUnit(PpnVo vo);

    /**
     * 删除采购价格单
     * @param unitId
     * @param ppnNum
     * @return
     */
    int deleteByPpn(String unitId,String ppnNum);
}