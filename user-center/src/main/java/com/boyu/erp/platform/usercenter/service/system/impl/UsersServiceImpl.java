package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.model.system.OrdinaryDomainModel;
import com.boyu.erp.platform.usercenter.service.system.SysPrivilegeSerivce;
import com.boyu.erp.platform.usercenter.service.system.UserScopeServer;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.LoginModel;
import com.boyu.erp.platform.usercenter.vo.PrivModel;
import com.boyu.erp.platform.usercenter.vo.RolePrivModel;
import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDomainMapper domainMapper;

    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysUnitPaMapper unitPaMapper;
    @Resource
    private SysPrivilegeSerivce sysPrivilegeSerivce;
    @Autowired
    private SysUserPaMapper userPaMapper;

    @Autowired
    private SysPrsnlMapper prsnlMapper;

    @Autowired
    private SysUserPrivMapper userPrivMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUnitPrivMapper unitPrivMapper;

    @Autowired
    private UserScopeServer userScopeServer;

    /**
     *
     */
    @Override
    @Transactional(readOnly = true)
    public SysUser getAdmin(SysUser user) {
        return userMapper.getAdmin(user);
    }

    /**
     * 功能描述:  查询是否是超级管理员
     *
     * @param user (用户对象)
     * @return b ( false 不是管理员)
     * @auther: CLF
     * @date: 2019/7/13 9:18
     */
    @Override
    public boolean getIsAdmin(SysUser user) {
        if (userMapper.getAdmin(user) != null && userMapper.getAdmin(user).getOprId() == -1L) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:  查询是否是管理员
     *
     * @param user (用户对象)
     * @return b ( false 不是管理员)
     * @auther: CLF
     * @date: 2019/8/6 14:21
     */
    @Override
    @Transactional(readOnly = true)
    public boolean getIsLeader(SysUser user) {
        //系统组织操作员Id为-1
        if (userMapper.getAdmin(user) != null && userMapper.getAdmin(user).getOprId() != -1L) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:  查询是否是系统用户
     *
     * @param user (用户对象)
     * @return b ( false 不是系统用户)
     * @auther: CLF
     * @date: 2019/8/6 14:23
     */
    @Override
    @Transactional(readOnly = true)
    public boolean getIsSystemUser(SysUser user) {
        if (userMapper.getAdminUser(user) != null && userMapper.getAdminUser(user).getOprId() != -1L) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:  查询是否是普通用户
     *
     * @param user (用户对象)
     * @return b ( false 不是系统用户)
     * @auther: CLF
     * @date: 2019/8/6 14:23
     */
    @Override
    public boolean getIsUser(SysUser user) {
        if (!getIsAdmin(user) && !getIsLeader(user) && !getIsSystemUser(user)) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述: 普通用户切换领域(免输入登录)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 10:04
     */
    @Override
    public SysUser getOrdinaryLogin(OrdinaryDomainModel model) {
        SysUser sysUser = userMapper.getOrdinaryLogin(model);
        setUser(sysUser);
        return sysUser;

    }


    /**
     * 登录
     */
    @Override
    public SysUser systemLogin(LoginModel loginModel) {

        SysUser sysUser = userMapper.systemLogin(loginModel);
        setUser(sysUser);
        return sysUser;
    }

    /**
     * 登录用户设置领域、组织、权限等信息
     */
    public void setUser(SysUser sysUser) {

        if (sysUser != null) {
            sysUser.setDomain(domainMapper.selectByPrimaryKey(sysUser.getOwnerId()));
            sysUser.setUnit(unitMapper.selectByPrimaryKey(sysUser.getOwnerId()));
            sysUser.setPrsnl(prsnlMapper.selectById(sysUser.getUserId()));
            /**
             *查询用户在当前组织拥有的权限集合
             * */
            sysUser.setPrivilegeSet(getUserDomainPriv(sysUser));
            setIsType(sysUser);

        }
    }

    /**
     * 功能描述:  设置用户身份标识
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 19:58
     */
    public void setIsType(SysUser sysUser) {
        if (this.getIsAdmin(sysUser)) {
            sysUser.setIsType("A");
        }
        if (this.getIsLeader(sysUser)) {
            sysUser.setIsType("L");
        }
        if (this.getIsUser(sysUser)) {
            sysUser.setIsType("S");
        }
        if (this.getIsSystemUser(sysUser)) {
            sysUser.setIsType("AS");
        }
    }


    /**
     * userID查询
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysUser> selectByUserId(SysUser user) {
        return userMapper.selectByUserId(user);
    }


    /**
     * 查询全部
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysUser> selectAll(Integer page, Integer size, SysUser user) {

        //开启分页查询，写在查询语句上方
        //只有紧跟在PageHelper.startPage方法后的第一个Mybatis的查询（Select）方法会被分页。
        PageHelper.startPage(page, size);
        List<SysUser> resultList = userMapper.selectAll(user);

        //组装一个对象既

        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(resultList);
        return pageInfo;
    }


    /**
     * 添加
     */
    @Override
    public int insertSelective(SysUser record) {
        return userMapper.insertSelective(record);
    }

    /**
     * 主键条件查询
     */
    @Override
    @Transactional(readOnly = true)
    public SysUser selectByPrimaryKey(SysUserKey key) {
        return userMapper.selectByPrimaryKey(key);
    }

    /**
     * 修改
     */
    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 修改密码
     */
    @Override
    public int updatePswd(SysUser record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 修改用户
     */
    @Override
    public int updateUser(SysUser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * (批量)修改用权限
     */
    @Override
    public void updateUserPriv(RolePrivModel model, SysUser user) {
        //用户授权
        if (StringUtils.isNotEmpty(model.getAdd()) && model.getAdd().equals("add")) {
            if (model.getPrivadd().size() > 0) {
                for (SysPrivilege privilege : model.getPrivadd()) {
                    //1. 增加用户权限分配表
                    SysUserPa userPa = new SysUserPa();
                    userPa.setPrivId(privilege.getPrivId());//权限Id
                    userPa.setUserId(model.getUser().getUserId());
                    userPa.setOwnerId(model.getUser().getOwnerId());
                    userPa.setUnitId(model.getUser().getOwnerId());
                    userPa.setUgId(-1L);//分组
                    userPa.setGrRv("G");//授权
                    userPa.setUnlimited("T");//是否全局
                    userPa.setOprId(user.getUserId());//操作员Id
                    userPaMapper.insertSelective(userPa);
                    //2.增加用户权限表
                    SysUserPrivKey privKey = new SysUserPrivKey();
                    privKey.setUgId(-1L);
                    privKey.setUnlimited("T");
                    privKey.setUnitId(user.getOwnerId());//组织Id为操作员属主Id
                    privKey.setUserId(model.getUser().getUserId());
                    privKey.setOwnerId(model.getUser().getOwnerId());
                    privKey.setPrivId(privilege.getPrivId());
                    userPrivMapper.insertSelective(privKey);
                    //管理员用户将权限分配给其组织

                    if (userMapper.getAdmin(model.getUser()) != null) {
                        SysUnitPrivKey unitPrivKey = new SysUnitPrivKey();
                        unitPrivKey.setUnitId(model.getUser().getOwnerId());
                        unitPrivKey.setPrivId(privilege.getPrivId());
                        unitPrivMapper.insertSelective(unitPrivKey);
                        SysUnitPa pa = new SysUnitPa();
                        pa.setUnitId(model.getUser().getOwnerId());
                        pa.setPrivId(privilege.getPrivId());
                        insertUnitPa(user, pa);
                    }

                }
            }
        }
        //用户权限回收
        if (StringUtils.isNotEmpty(model.getDelete()) && model.getDelete().equals("delete")) {
            if (model.getPrivdelete().size() > 0) {
                for (SysPrivilege privilege : model.getPrivdelete()) {
                    //1. 修改用户权限分配表
                    SysUserPa userPa = new SysUserPa();
                    userPa.setPrivId(privilege.getPrivId());//权限Id
                    userPa.setUserId(model.getUser().getUserId());
                    userPa.setOwnerId(model.getUser().getOwnerId());
                    userPa.setUnitId(model.getUser().getOwnerId());
                    userPa.setUgId(-1L);//分组
                    userPa.setGrRv("R");//回收权限
                    userPa.setUnlimited("T");//是否全局
                    userPa.setOprId(user.getUserId());//操作员Id
                    userPaMapper.accreditRecycle(userPa);
                    //2.删除用户权限表 对应关系
                    SysUserPrivKey privKey = new SysUserPrivKey();
                    // privKey.setUgId("-1");
                    privKey.setUnlimited("T");
                    // privKey.setUnitId(user.getOwnerId());//组织Id为操作员属主Id
                    privKey.setUserId(model.getUser().getUserId());
                    privKey.setOwnerId(model.getUser().getOwnerId());
                    privKey.setPrivId(privilege.getPrivId());
                    userPrivMapper.delete(privKey);
                    //管理员权限回收
                    if (userMapper.getAdmin(model.getUser()) != null) {

                        deleteUnitPa(user, model.getPrivdelete(), model.getUser().getOwnerId());
                    }

                }
            }
        }
    }

    /**
     * 批量修改用户角色
     */
    @Override
    public void updateUserRole(PrivModel model, SysUser user) {
        /**
         * 用户授角色
         * 1. 查看角色数据范围
         * 2.判断是否能授予用户
         * */
        if (StringUtils.isNotEmpty(model.getAdd()) && model.getAdd().equals("add")) {

            if (model.getRoleadd().size() > 0) {
                for (SysRole role : model.getRoleadd()) {
                    //1. 增加用户角色权限分配表
                    SysUserPa userPa = new SysUserPa();
                    userPa.setRoleId(role.getRoleId());//角色Id
                    userPa.setUserId(model.getUser().getUserId());
                    userPa.setOwnerId(model.getUser().getOwnerId());
                    userPa.setUnitId(model.getUser().getOwnerId());
                    userPa.setUgId(-1L);//分组
                    userPa.setGrRv("G");//授权
                    userPa.setUnlimited("T");//是否全局
                    userPa.setOprId(user.getUserId());//操作员Id
                    userPaMapper.insertSelective(userPa);

                    //2.增加用户角色表
                    SysUserRoleKey roleKey = new SysUserRoleKey();
                    roleKey.setUserId(model.getUser().getUserId());
                    roleKey.setUnlimited("T");
                    roleKey.setUgId(-1L);
                    roleKey.setOwnerId(model.getUser().getOwnerId());
                    roleKey.setUnitId(model.getUser().getOwnerId());
                    roleKey.setRoleId(role.getRoleId());
                    userRoleMapper.insertSelective(roleKey);

                }
            }
        }
        if (StringUtils.isNotEmpty(model.getDelete()) && model.getDelete().equals("delete")) {
            if (model.getRoledelete().size() > 0) {
                for (SysRole role : model.getRoledelete()) {
                    //1. 修改用户角色权限分配表
                    SysUserPa userPa = new SysUserPa();
                    userPa.setRoleId(role.getRoleId());//角色Id
                    userPa.setUserId(model.getUser().getUserId());
                    userPa.setOwnerId(model.getUser().getOwnerId());
                    userPa.setUnitId(model.getUser().getOwnerId());
                    userPa.setUgId(-1L);//分组
                    userPa.setGrRv("R");//授权回收
                    userPa.setUnlimited("T");//是否全局
                    userPa.setOprId(user.getUserId());//操作员Id
                    userPaMapper.accreditRecycle(userPa);

                    //2.删除用户角色表
                    SysUserRoleKey roleKey = new SysUserRoleKey();
                    roleKey.setUserId(model.getUser().getUserId());
                    roleKey.setUnlimited("T");
                    roleKey.setUgId(-1L);
                    roleKey.setOwnerId(model.getUser().getOwnerId());
                    roleKey.setUnitId(model.getUser().getOwnerId());
                    roleKey.setRoleId(role.getRoleId());
                    userRoleMapper.deleteByPrimaryKey(roleKey);

                }
            }
        }


    }

    @Override
    public void deleteUser(SysUser user) {
        user.setUserStatus("D");
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public SysUser systemByUser(LoginModel user) {
        return userMapper.systemByUser(user);
    }

    @Override
    public int userLoader(List<SysUser> users) {
        int count = 0;
        for (SysUser u : users) {
            u.setUserStatus("A");
            userMapper.updateByPrimaryKeySelective(u);
            count++;
        }
        return count;
    }

    /**
     * 添加组织权限分配表数据
     */
    private void insertUnitPa(SysUser user, SysUnitPa pa) {

        List<SysUnitPa> sysUnitPas = unitPaMapper.selectAll(pa);
        pa.setOpTime(new Date());
        pa.setOprId(user.getUserId());
        pa.setGrRv("G");
        if (!CollectionUtils.isEmpty(sysUnitPas)) {//已存在数据  修改
            //修改 重新授权
            unitPaMapper.accreditRecycle(pa);
        } else {
            //添加
            unitPaMapper.insertSelective(pa);
        }
    }

    /**
     * 删除管理员用户权限(等同删除组织权限)
     * 1.修改组织权限分配表数据
     * 2.修改用户权限分配表
     * 3.删除用户权限
     * 4删除组织权限
     */
    public void deleteUnitPa(SysUser user, List<SysPrivilege> privdelete, Long ownerId) {
        for (SysPrivilege privilege : privdelete) {
            SysUnitPa pa = new SysUnitPa();
            pa.setUnitId(ownerId);
            pa.setPrivId(privilege.getPrivId());
            pa.setOpTime(new Date());
            pa.setOprId(user.getUserId());
            pa.setGrRv("R");
            unitPaMapper.accreditRecycle(pa);
            SysUnitPrivKey key = new SysUnitPrivKey();
            key.setPrivId(privilege.getPrivId());
            key.setUnitId(ownerId);
            unitPrivMapper.deleteUnitPriv(key);
        }

    }


    /**
     * 功能描述: 登录查询用户在当前领域拥有的权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/14 14:46
     */
    public List<SysPrivilege> getUserDomainPriv(SysUser sysUser) {
        UnitDomainVo vo = new UnitDomainVo();
        List<SysPrivilege> list = null;
        //系统组织Id为1L简单起见硬编码 不去查询()
        vo.setUnitId(1L);
        Set<SysPrivilege> set = userScopeServer.getUnitScopePriv(vo, sysUser, "login");

        if (CollectionUtils.isNotEmpty(set)) {
            list = new ArrayList<>(set);
        }
        return list;
    }
}