package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUrl;

import java.util.List;

public interface SysUrlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUrl record);

    int insertSelective(SysUrl record);

    SysUrl selectByPrimaryKey(Long id);

    int updateUrl(SysUrl record);


    List<SysUrl> findAll(SysUrl url);

    SysUrl findUrl(SysUrl url);
}