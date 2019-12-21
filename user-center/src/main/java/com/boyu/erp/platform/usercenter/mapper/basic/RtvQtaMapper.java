package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.RtvQta;
import com.boyu.erp.platform.usercenter.entity.basic.RtvQtaPg;

import java.util.Map;

public interface RtvQtaMapper {
    int deleteByPrimaryKey(RtvQta key);

    int insert(RtvQta record);

    int insertSelective(RtvQta record);

    RtvQta selectByPrimaryKey(RtvQta key);

    int updateByPrimaryKeySelective(RtvQta record);

    int updateByPrimaryKey(RtvQta record);

    //通过可退未决金额修改可退金额
    int updateByRtvQtaPg(RtvQtaPg pg);

    //累计可退金额
    int insertOrUpdate(Map<String,Object> map);
}