package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;

import java.util.List;

public interface CaAccMapper {
    int deleteByPrimaryKey(CaAcc key);

    int insert(CaAcc record);

    int insertSelective(CaAcc record);

    CaAcc selectByPrimaryKey(CaAcc key);

    int updateByPrimaryKeySelective(CaAcc record);

    int updateByPrimaryKey(CaAcc record);

    //查询出所有与单据相关联的数据
    List<CaAcc> selectByDoc(CaAcc key);

    /**
     * 取消授信时删除
     */
    int deleteByRevoke(List<CaAcc> list);

    /**
     * 往来账户中查询授信明细
     * @param vo
     * @return
     */
    List<CaAcc> selectALL(CaVo vo);
}