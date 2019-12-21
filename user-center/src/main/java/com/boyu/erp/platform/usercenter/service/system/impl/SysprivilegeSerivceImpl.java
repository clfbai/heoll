package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.service.system.SysPrivilegeSerivce;
import com.boyu.erp.platform.usercenter.vo.PrivModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SysprivilegeSerivceImpl implements SysPrivilegeSerivce {
    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    @Autowired
    private SysUnitPrivMapper unitPrivMapper;

    @Autowired
    private SysRolePrivMapper rolePrivMapper;

    @Autowired
    private SysUnitPaMapper unitPaMapper;

    @Autowired
    private SysUserPaMapper userPaMapper;

    @Autowired
    private SysUserPrivMapper userPrivMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysPrivDepMapper depMapper;

    /**
     * 查询用户是否是管理员是管理返回组织权限
     * 不是返回用户权限
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> getYnAdmin(SysUser user) {

        if (userMapper.getAdmin(user) != null) {
            if (userMapper.getAdmin(user).getOprId() == -1) {
                //超级管理员权限
                return getByAllauthoritys(user);
            }
            //组织管理员权限
            return sysPrivilegeMapper.UnitPriv(user);
        } else {
            //普通用户权限
            return sysPrivilegeMapper.getuserPriv(user);
        }

    }


    //用户权限查询
    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> getAuthoritys(SysUser user) {
        return getYnAdmin(user);
    }

    /**
     * 用户所有权限分页
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysPrivilege> getRrivAll(Integer page, Integer size, SysUser user) {
        List<SysPrivilege> resultList = new ArrayList<>();
        PageInfo<SysPrivilege> pageInfo = null;
        if (userMapper.getAdmin(user) != null) {
            if (userMapper.getAdmin(user).getOprId() == -1) {
                //系统管理员权限
                PageHelper.startPage(page, size);
                resultList = sysPrivilegeMapper.getByAllauthoritys(user);
                pageInfo = new PageInfo<SysPrivilege>(resultList);
            } else {
                //组织管理员权限
                PageHelper.startPage(page, size);
                resultList = sysPrivilegeMapper.UnitPriv(user);
                pageInfo = new PageInfo<SysPrivilege>(resultList);
            }
        } else {
            //普通用户权限
            PageHelper.startPage(page, size);
            resultList = sysPrivilegeMapper.getuserPriv(user);
            pageInfo = new PageInfo<>(resultList);
        }
        return pageInfo;
    }


    /**
     * 查询权限表所有
     * 此项操作只对系统管理员进行
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> getByAllauthoritys(SysUser user) {
        return sysPrivilegeMapper.getByAllauthoritys(user);
    }

    /**
     * 增加权限
     * 1.增加权限表
     * 2.增加组织权限分配表
     * 3.增加用户权限分配表
     * 4.增加用户权限对应表
     * 5.增加组织权限对应表
     */
    @Override
    public void addAuthority(SysPrivilege record, SysUser user)throws ServiceException {
        if (sysPrivilegeMapper.selectPrivId(record.getPrivId()) != null) {
          throw new ServiceException("403","权限Id已存在");
        }
        //步骤1 增加权限表
        sysPrivilegeMapper.addAuthority(record);
        //步骤2增加组织权限分配表
        SysUnitPa pa = new SysUnitPa();
        pa.setPrivId(record.getPrivId());
        pa.setUnitId(user.getOwnerId());
        pa.setOprId(user.getUserId());
        pa.setGrRv("G");
        unitPaMapper.insertSelective(pa);
        //步骤3增加用户权限分配表
        SysUserPa userPa = new SysUserPa();
        userPa.setGrRv("G");
        userPa.setUserId(user.getUserId());
        userPa.setOwnerId(user.getOwnerId());
        userPa.setUnitId(user.getOwnerId());
        userPa.setPrivId(record.getPrivId());
        userPa.setOprId(user.getUserId());
        userPa.setUnlimited("T");
        userPa.setUgId(-1L);
        userPaMapper.insertSelective(userPa);
        //步骤4增加用户权限
        SysUserPrivKey privKey = new SysUserPrivKey();
        privKey.setOwnerId(user.getOwnerId());
        privKey.setUserId(user.getUserId());
        privKey.setPrivId(record.getPrivId());
        privKey.setUnlimited("T");
        privKey.setUgId(-1L);
        privKey.setUnitId(user.getOwnerId());
        userPrivMapper.insertSelective(privKey);
        //步骤5
        SysUnitPrivKey unitPrivKey = new SysUnitPrivKey();
        unitPrivKey.setPrivId(record.getPrivId());
        unitPrivKey.setUnitId(user.getOwnerId());
        unitPrivMapper.insertSelective(unitPrivKey);
    }

    /**
     * 修改权限
     * 说明：      1. 修改权限表 权限(delete语句)
     */
    @Override
    public int updateByPrimaryAuthority(SysUser user, List<SysPrivilege> record) {
        int a = 0;
        for (SysPrivilege page : record) {
            SysPrivilege privilege = new SysPrivilege();
            sysPrivilegeMapper.updateByPrimaryAuthority(page);
            a++;
        }
        return a;
    }


    /**
     * 删除权限
     * 说明：1. 物理删除权限表 权限(delete语句)
     * 2. 物理删除角色权限表 角色权限对应关系(delete语句)
     * 3. 物理删除组织权限表 组织权限对应关系(delete语句)
     * 4. 修改组织权限分配表
     * 5. 修改用户权限分配表
     * 6. 删除相应权限依赖
     */
    @Override
    public void deletePriv(SysPrivilege privilege, SysUser user) {
        //步骤1
        SysUnitPrivKey unitPrivKey = new SysUnitPrivKey();
        unitPrivKey.setPrivId(privilege.getPrivId());
        unitPrivKey.setUnitId(user.getOwnerId());
        unitPrivMapper.deleteUnitPriv(unitPrivKey);
        //步骤2
        SysRolePrivKey key = new SysRolePrivKey();
        key.setPrivId(privilege.getPrivId());
        rolePrivMapper.deleteKey(key);
        //步骤3
        sysPrivilegeMapper.deletePriv(privilege);
        //步骤4
        SysUnitPa unitPa = new SysUnitPa();
        unitPa.setUnitId(user.getOwnerId());
        unitPa.setPrivId(privilege.getPrivId());
        List<SysUnitPa> unitPas = unitPaMapper.selectAll(unitPa);
        for (SysUnitPa pa : unitPas) {
            pa.setGrRv("R");
            unitPaMapper.updateByPrimaryKeySelective(pa);
        }
        //步骤5
        SysUserPa userPa = new SysUserPa();
        userPa.setUserId(user.getUserId());
        userPa.setOwnerId(user.getOwnerId());
        for (SysUserPa pa : userPaMapper.selectAll(userPa)) {
            pa.setGrRv("R");
            userPaMapper.updateByPrimaryKeySelective(pa);
        }
        //步骤6
        SysPrivDepKey keys = new SysPrivDepKey();
        keys.setPrivId(privilege.getPrivId());
        depMapper.deletePrivDel(keys);

    }

    /**
     * 查询权限类别
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> getAllType() {
        return sysPrivilegeMapper.selectPrivType();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> getAllTypeScope() {
        return sysPrivilegeMapper.getTypeScope();
    }


    /**
     * 查询角色权限
     *
     * @param role
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> getRolePriv(SysRole role) {
        return sysPrivilegeMapper.selectRolePriv(role);
    }

    /**
     * 权限角色回收
     *
     * @param privilege
     * @return
     */
    @Override
    public void updatePrivRole(PrivModel privilege) {
        //判断是否添加

        if (privilege.getRoleadd().size() > 0) {
            for (SysRole role : privilege.getRoleadd()) {
                SysRolePrivKey record = new SysRolePrivKey();
                record.setPrivId(privilege.getPrivId());
                record.setRoleId(role.getRoleId());
                rolePrivMapper.insertSelective(record);
            }
        }
        //判断是否删除
        if (StringUtils.isNotEmpty(privilege.getDelete()) && privilege.getDelete().equals("delete")) {
            if (privilege.getRoledelete().size() > 0) {
                for (SysRole role : privilege.getRoledelete()) {
                    SysRolePrivKey record = new SysRolePrivKey();
                    record.setPrivId(privilege.getPrivId());
                    record.setRoleId(role.getRoleId());
                    rolePrivMapper.deleteByPrimaryKey(record);
                }
            }
        }

    }

    /**
     * 查询是否具有操作权限表权限
     */
    @Override
    @Transactional(readOnly = true)
    public boolean selectPriv(SysUser user) {
        SysPrivilege ps = new SysPrivilege();
        ps.setPrivId("maxpriv");
        user.setPrivilege(ps);
        for (SysPrivilege p : getYnAdmin(user)) {
            if (p.getPrivId().equalsIgnoreCase("maxpriv")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查看权限对应用户
     *
     * @param privilege
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPrsnl> getPrivuser(SysPrivilege privilege) {

        return sysPrivilegeMapper.getPrivUser(privilege);
    }

    /**
     * 查看权限对应组织
     *
     * @param privilege
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysUnit> getPrivUnit(SysPrivilege privilege) {
        return sysPrivilegeMapper.getPrivUnit(privilege);
    }

    /**
     * 查询操作权限
     */
    @Override
    @Transactional(readOnly = true)
    public SysPrivilege OperationAuthorityPriv(String privilegeId) {
        return sysPrivilegeMapper.OperationAuthorityPriv(privilegeId);
    }

    @Override
    public int addOperationAuthority(SysPrivilege pr) {
        return sysPrivilegeMapper.addAuthority(pr);
    }


}
