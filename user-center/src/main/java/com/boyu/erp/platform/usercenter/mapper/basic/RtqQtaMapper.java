package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.RtqQta;
import com.boyu.erp.platform.usercenter.entity.basic.RtqQtaPg;

import java.util.List;
import java.util.Map;

public interface RtqQtaMapper {
    int deleteByPrimaryKey(RtqQta key);

    int insert(RtqQta record);

    int insertSelective(RtqQta record);

    RtqQta selectByPrimaryKey(RtqQta key);

    int updateByPrimaryKeySelective(RtqQta record);

    int updateByPrimaryKey(RtqQta record);

    //批量修改
    int updateByBill(List<RtqQta> list);

    /**
     * 通过可退未决数量修改
     * @param list
     * @return
     */
    int updateByRtqQtaPg(List<RtqQtaPg> list);

    /**
     * 新增或者修改
     * @param map
     * @return
     */
    int insertOrUpdate(Map<String,Object> map);
}