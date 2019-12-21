package com.boyu.erp.platform.usercenter.mapper.collarUse;

import com.boyu.erp.platform.usercenter.entity.collarUse.Arq;
import com.boyu.erp.platform.usercenter.vo.collarUse.ArqVo;

import java.util.List;

public interface ArqMapper {
    int deleteByPrimaryKey(Arq record);

    int insert(Arq record);

    int insertSelective(ArqVo vo);

    Arq selectByPrimaryKey(Arq record);

    int updateByPrimaryKeySelective(ArqVo vo);

    int updateByPrimaryKey(Arq record);

    List<ArqVo> selectALL(ArqVo vo);

    List<ArqVo> selectByUnit(ArqVo vo);

    int deleteByArqVo(String arqNum,String unitId);
}