package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitPrivKey;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivVo;

import java.util.List;

public interface SysUnitPrivMapper {

    /**
     * 查询组织权限
     */
    public List<SysPrivilege> selectAll(UnitPrivVo unitPrivVo);

    /**
     * 超级管理员查询
     */
    public List<SysPrivilege> getByAll(UnitPrivVo unitPrivVo);

    /**
     * 添加
     */
    public int insertSelective(SysUnitPrivKey record);

    /**
     * 删除组织权限
     */
    public int deleteUnitPriv(SysUnitPrivKey sysUnitPriv);

    /**
     * 功能描述: 查询组织拥有直接权限(不包含依赖权限)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 15:15
     */
    List<SysPrivilege> getPriv(Long unitId);
}