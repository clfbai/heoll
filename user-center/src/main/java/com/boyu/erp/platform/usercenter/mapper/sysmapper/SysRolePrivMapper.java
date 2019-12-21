package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysRolePrivKey;

public interface SysRolePrivMapper {

    public int insert(SysRolePrivKey record);

    public int insertSelective(SysRolePrivKey record);
    /**
    * 删除权限对应的角色（需要参数:权限Id)
    * */
    public int deleteKey(SysRolePrivKey key);

    /**
     * 删除的角色权限（需要参数角色ID ,权限Id)
     * */
    public int deleteByPrimaryKey(SysRolePrivKey key);


}