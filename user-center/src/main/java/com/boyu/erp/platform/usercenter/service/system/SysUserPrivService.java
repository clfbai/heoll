package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserPrivKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.system.UserPrivModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysUserPrivService {


    /**
     * 查询所有
     */
    public PageInfo<SysPrivilege> selectAll(Integer page, Integer size, SysUserPrivKey userPrivKey);

    /**
     * 添加用户权限
     */
    public int insertSelective(List<SysUserPrivKey> record, SysUser user);

    /**
     * 删除用户权限
     */
    public int deleteUserPriv(List<SysUserPrivKey> record, SysUser user);

    /**
     * 功能描述: 批量修改用户权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 19:56
     */
    void updateUserPriv(UserPrivModel model, SysUser user) throws ServiceException;
}
