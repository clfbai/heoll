package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Pso;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;

import java.util.List;

public interface PsoMapper {
    int deleteByPrimaryKey(String psoNum);

    int insert(Pso record);

    int insertSelective(Pso record);

    Pso selectByPrimaryKey(String psoNum);

    int updateByPrimaryKeySelective(Pso record);

    int updateByPrimaryKey(Pso record);

    int insertByPso(PsoVo vo);

    int updateByPso(PsoVo vo);

    //功能中操作更细状态
    int updateByState(PsoVo vo);

}