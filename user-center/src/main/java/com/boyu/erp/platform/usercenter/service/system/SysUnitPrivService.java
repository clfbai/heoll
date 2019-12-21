package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitPrivKey;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivModel;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivVo;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface SysUnitPrivService {


    /**
     * 添加
     */
    public int insertSelective(List<SysUnitPrivKey> record, SysUser user);

    /**
     * 查询
     */
    public PageInfo<SysPrivilege> selectAll(Integer page, Integer size, UnitPrivVo unitPrivVo, SysUser user);


    /**
     * 删除组织权限
     */
    public int deleteUnitPriv(List<SysUnitPrivKey> sysUnit, SysUser user);

    /**
     * 修改组织的权限和角色
     */
    public int updateUnitRriv(UnitPrivModel model, SysUser user);

    /**
     * 查询组织权限不分页集合
     */
    public List<SysPrivilege> getUnitPrivAll(Long unitId);

    /**
     * 功能描述: 查询组织所有权限(判断是否是系统组织)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 16:42
     */
    List<SysPrivilege> getUnitPriv(Long unitId);


}
