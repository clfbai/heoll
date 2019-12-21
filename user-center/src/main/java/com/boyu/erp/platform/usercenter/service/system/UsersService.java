package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserKey;
import com.boyu.erp.platform.usercenter.model.system.OrdinaryDomainModel;
import com.boyu.erp.platform.usercenter.vo.LoginModel;
import com.boyu.erp.platform.usercenter.vo.PrivModel;
import com.boyu.erp.platform.usercenter.vo.RolePrivModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UsersService {

    /**
     * 登录
     */
    SysUser systemLogin(LoginModel loginModel);


    /**
     * userID查询
     */
    List<SysUser> selectByUserId(SysUser user);

    /**
     * 查询所有
     */
    PageInfo<SysUser> selectAll(Integer page, Integer size, SysUser user);

    /**
     * 添加
     */
    int insertSelective(SysUser record);

    /**
     * 主键查询
     */
    SysUser selectByPrimaryKey(SysUserKey key);

    /**
     * 修改
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 修改密码
     */
    int updatePswd(SysUser record);

    /**
     * 修改用户
     */
    int updateUser(SysUser user);

    /**
     * 修改用户权限
     */
    void updateUserPriv(RolePrivModel model, SysUser user);

    /**
     * 修改用户权限
     */
    void updateUserRole(PrivModel model, SysUser user);

    /**
     * 删除用户
     */
    void deleteUser(SysUser user);

    SysUser systemByUser(LoginModel user);

    int userLoader(List<SysUser> users);

    /**
     * 查询是否是超级管理员或管理员
     */
    SysUser getAdmin(SysUser user);

    /**
     * 功能描述:  查询是否是超级管理员
     *
     * @param user (用户对象)
     * @return b ( false 不是超级管理员)
     * @auther: CLF
     * @date: 2019/8/6 14:18
     */
    boolean getIsAdmin(SysUser user);


    /**
     * 功能描述:  查询是否是管理员
     *
     * @param user (用户对象)
     * @return b ( false 不是管理员)
     * @auther: CLF
     * @date: 2019/8/6 14:21
     */
    boolean getIsLeader(SysUser user);

    /**
     * 功能描述:  查询是否是系统用户
     *
     * @param user (用户对象)
     * @return b ( false 不是系统用户)
     * @auther: CLF
     * @date: 2019/8/6 14:23
     */
    boolean getIsSystemUser(SysUser user);

    /**
     * 功能描述:查询是否是普通组织普通用户
     *
     * @param user
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 10:03
     */
    boolean getIsUser(SysUser user);


    /**
     * 功能描述: 普通用户切换领域(免输入登录)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 10:04
     */
    SysUser getOrdinaryLogin(OrdinaryDomainModel model);


}
