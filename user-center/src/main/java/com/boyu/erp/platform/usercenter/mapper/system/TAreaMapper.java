package com.boyu.erp.platform.usercenter.mapper.system;

import com.boyu.erp.platform.usercenter.entity.system.TArea;

import java.util.List;
/**
 *
 *
 * @Description: 查询省市县Mapper
 * @auther: CLF
 * @date: 2019/7/19 14:44
 */
public interface TAreaMapper {

    TArea selectByPrimaryKey(Integer areaId);

    List<TArea> getTArtea(TArea tArea);
}