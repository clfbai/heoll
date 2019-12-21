package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaFrz;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;

import java.util.List;

public interface CaFrzMapper {
    int deleteByPrimaryKey(CaFrz key);

    int insert(CaFrz record);

    int insertSelective(CaFrz record);

    CaFrz selectByPrimaryKey(CaFrz key);

    int updateByPrimaryKeySelective(CaFrz record);

    int updateByPrimaryKey(CaFrz record);

    /**
     * 查询往来账户中冻结明细
     * @param vo
     * @return
     */
    List<CaFrz> selectALL(CaVo vo);
}