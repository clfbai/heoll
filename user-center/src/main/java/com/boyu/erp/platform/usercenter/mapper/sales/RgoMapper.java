package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.Rgo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;

import java.util.List;

public interface RgoMapper {
    int deleteByPrimaryKey(Rgo record);

    int insert(Rgo record);

    int insertSelective(Rgo record);

    Rgo selectByPrimaryKey(Rgo record);

    int updateByPrimaryKeySelective(Rgo record);

    int updateByPrimaryKey(Rgo record);

    //超级管理员查询
    List<RgoVo> selectALL(RgoVo vo);

    //组织/用户查询
    List<RgoVo> selectByUnit(RgoVo vo);

    //清除调配单明细更新主表数据
    int updateByDeleteDtl(RgoVo vo);

    //重做单据时更新
    int updateByRedo(RgoVo vo);

    //查询单条记录
    RgoVo selectByOnly(RgoVo vo);
}