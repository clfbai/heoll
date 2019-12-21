package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.UserRoleModel;
import com.boyu.erp.platform.usercenter.vo.system.UserRoleVo;
import com.github.pagehelper.PageInfo;


public interface SysUserRoleService {

    /**
     * 查询所有用户角色
     *
     * @param
     * @return
     */
    public PageInfo<SysRole> selectAll(Integer page, Integer size, UserRoleVo userRoleVo);

    /**
     * 添加用户角色
     *
     * @param record
     * @return
     */
    int insert(UserRoleModel record, SysUser user);


    /**
     * 删除用户角色
     *
     * @param key
     * @return
     */
    public int deleteByPrimaryKey(UserRoleModel key, SysUser user);

    /**
     * 功能描述:  批量修改用户角色
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 19:23
     */
    void updateUserRole(UserRoleModel userRole, SysUser sessionSysUser);
}
