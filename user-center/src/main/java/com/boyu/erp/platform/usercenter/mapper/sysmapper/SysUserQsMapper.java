package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUserQs;
import com.boyu.erp.platform.usercenter.entity.system.SysUserQsKey;

public interface SysUserQsMapper {

    public SysUserQs selectByPrimaryKey(SysUserQsKey key);

    public int insert(SysUserQs record);

    public int insertSelective(SysUserQs record);

    public int updateByPrimaryKeySelective(SysUserQs record);

    public int updateByPrimaryKey(SysUserQs record);

    public int deleteByPrimaryKey(SysUserQsKey key);
}