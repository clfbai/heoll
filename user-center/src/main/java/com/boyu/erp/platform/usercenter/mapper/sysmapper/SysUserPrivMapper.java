package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUgDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserPrivKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserPrivMapper {
    /**
     * 查询所有
     */
    List<SysUserPrivKey> selectAll(SysUserPrivKey userPrivKey);

    /**
     * 添加
     */
    int insertSelective(SysUserPrivKey record);

    /**
     * 删除用户权限
     */
    int delete(SysUserPrivKey record);

    /**
     * 删除组织权限时回收组织下所有用户权限
     */
    int deleteUintPriv(SysUserPrivKey privKey);


    /**
     * 功能描述:  查询用户在登录的领域是否有范围权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 19:59
     */
    SysUserPrivKey getScopeAll(SysUser user);

    /**
     * 功能描述: 查询用户在指定组织拥有的全局权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 11:28
     */
    List<SysPrivilege> getScopeUnitPriv(SysUserPrivKey privKey);

    /**
     * 功能描述 : 用户批量添加权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 20:05
     */
    int insertList(@Param("list") List<SysUserPrivKey> list);

    /**
     * 功能描述: 批量删除用户角色
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 10:26
     */
    int deleteList(@Param("list") List<SysUserPrivKey> list);

    /**
     * 功能描述: 查询用户拥有哪些分组权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 10:27
     */
    List<SysUgDtl> getUserUg(SysUser user);

    /**
     * 功能描述:  查询用户分组权限集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 11:03
     */
    List<SysPrivilege> getUgLitPriv( Map<String,Object> map);

   /**
    *
    * 功能描述:  查询用户在指定组织拥有的所有权限
    *
    * @param:
    * @return:
    * @auther: CLF
    * @date: 2019/8/13 16:27
    */
    List<SysPrivilege> getScopeUnitPrivAll(SysUserPrivKey privKey);
}