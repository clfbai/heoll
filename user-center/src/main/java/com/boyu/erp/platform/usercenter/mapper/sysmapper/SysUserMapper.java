package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserKey;
import com.boyu.erp.platform.usercenter.model.UserModel;
import com.boyu.erp.platform.usercenter.model.system.OrdinaryDomainModel;
import com.boyu.erp.platform.usercenter.vo.LoginModel;

import java.util.List;

public interface SysUserMapper {

    /**
     * 根据prsnlID或userid查用户
     *
     * @param user
     * @return
     */
    public List<SysUser> selectByUserId(SysUser user);

    public List<SysUser> selectAll(SysUser user);

    public SysUser selectByPrimaryKey(SysUserKey key);

    public SysUser systemLogin(LoginModel loginModel);

    /**
     * 功能描述: 普通用户切换领域(免登陆)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 10:47
     */
    SysUser getOrdinaryLogin(OrdinaryDomainModel model);

    public int insertSelective(SysUser record);

    public int updateByPrimaryKeySelective(SysUser record);

    int deleteUserStatus(SysUser user);

    public SysUser systemByUser(LoginModel user);

    /**
     * 查询用户是否为管理员
     */
    public SysUser getAdmin(SysUser user);

    /**
     * 查询组织下用户
     */
    List<UserModel> getUnitUser(Long unitId);

    /**
     * 查询用户是否是系统管理组织用户
     */
    SysUser getAdminUser(SysUser user);

    /**
     * 根据用户Id属主Id查询用户
     */
    SysUser getUserIdAndOwnerId(Long userId, Long ownerId);



    /**
     * 功能描述: 查询组织管理员
     *
     * @param unitId (组织Id)
     * @return:
     * @auther: CLF
     * @date: 2019/11/22 10:10
     */
    SysUser selectUnitAdmin(Long unitId);

}