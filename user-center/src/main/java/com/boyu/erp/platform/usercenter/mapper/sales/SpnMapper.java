package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.Spn;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnScpVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnVo;

import java.util.List;

public interface SpnMapper {
    int deleteByPrimaryKey(Spn key);

    int insert(Spn record);

    int insertSelective(SpnVo record);

    Spn selectByPrimaryKey(Spn key);

    int updateByPrimaryKeySelective(SpnVo record);

    int updateByPrimaryKey(Spn record);

    //系统管理员查询
    List<SpnVo> selectALL(SpnVo vo);

    //组织查询
    List<SpnVo> selectByUnit(SpnVo vo);

    /**
     * 删除销售价格单
     * @param unitId
     * @param spnNum
     * @return
     */
    int deleteBySpn(String unitId,String spnNum);

    /**
     * 更改状态
     * @param vo
     * @return
     */
    int updateByState(SpnVo vo);
}