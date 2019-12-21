package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.Sat;
import com.boyu.erp.platform.usercenter.vo.SatVo;

import java.util.List;

/**
 * 配码
 */

public interface SatMapper {
    int deleteByPrimaryKey(Long satId);

    int insertSelective(Sat record);

    Sat selectByPrimaryKey(Long satId);

    int updateByPrimaryKeySelective(Sat record);

    List<Sat> selectAll(SatVo sat);

}