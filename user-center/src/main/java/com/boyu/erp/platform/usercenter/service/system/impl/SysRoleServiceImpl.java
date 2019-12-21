package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.service.system.SysPrivilegeSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysRoleService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.RolePrivModel;
import com.boyu.erp.platform.usercenter.vo.RoleUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;
    @Resource
    private SysPrivilegeMapper privilegeMapper;
    @Resource
    private SysUnitRoleMapper unitRoleMapper;
    @Resource
    private SysUserPaMapper userPaMapper;
    @Resource
    private SysUnitPaMapper unitPaMapper;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysRolePrivMapper rolePrivMapper;
    @Autowired
    private SysPrivilegeSerivce privilegeSerivce;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysRoleScopeMapper roleScopeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SysRole> AdminAndUser(SysUser user) {
        if (userMapper.getAdmin(user) != null && userMapper.getAdmin(user).getOprId() == -1) {
            //系统管理员角色
            return roleMapper.RoleTableAll(user);
        }
        //用户角色
        return roleMapper.selectAll(user);
    }

    /**
     * 查询是否具有修改角色表权限("maxrole  代表操作角色表权限" 此字段目前写死 以后读配置文件）
     */
    @Override
    @Transactional(readOnly = true)
    public boolean selectRoleTable(SysUser user) {
        SysPrivilege ps = new SysPrivilege();
        ps.setPrivId("maxrole");
        user.setPrivilege(ps);
        for (SysPrivilege p : privilegeSerivce.getYnAdmin(user)) {
            if (p.getPrivId().equalsIgnoreCase("maxrole")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询角色对应组织
     *
     * @param role
     */
    @Override
    public List<SysUnit> getRoleUint(SysRole role) {
        return roleMapper.getRoleUnit(role);
    }

    /**
     * 查询角色对应用户
     *
     * @param role
     */
    @Override
    public List<RoleUserVo> getRoleUser(SysRole role) {
        return roleMapper.getRoleUser(role);
    }

    /**
     * 角色分页
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysRole> selectAll(Integer page, Integer size, SysUser user) {
        PageInfo<SysRole> pageInfo;
        if (userMapper.getAdmin(user) != null && userMapper.getAdmin(user).getOprId() == -1) {
            //系统管理员角色
            PageHelper.startPage(page, size);
            List<SysRole> sysRoles = roleMapper.RoleTableAll(user);
            pageInfo = new PageInfo<>(sysRoles);

        } else {
            //用户角色
            PageHelper.startPage(page, size);
            List<SysRole> sysRoles = getRoleAll(user);
            pageInfo = new PageInfo<>(sysRoles);
        }
        return pageInfo;
    }

    /**
     * 用户所有角色
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysRole> getRoleAll(SysUser user) {
        return roleMapper.selectAll(user);
    }

    /**
     * 1.增加角色表
     * 2.增加用户角色表
     * 3.增加组织角色表
     * 4.增加组织角色分配表
     * 5.增加用户角色分配表
     */
    @Override
    public int addRole(SysRole role, SysUser user) throws ServiceException {
        if (roleMapper.selectRolrId(role.getRoleId()) != null) {
            throw new ServiceException("403", "角色ID:" + role.getRoleId() + " 已存在");
        }
        //SysUser adminUser = usersService.getAdmin(user);
        /**
         * 步骤2 增加用户角色表
         * */
        SysUserRoleKey roleKey = new SysUserRoleKey();
        roleKey.setUnlimited("T");//是否全局
        roleKey.setUnitId(user.getOwnerId());//属主Id
        roleKey.setUgId(-1L);//分组Id
        roleKey.setRoleId(role.getRoleId());
        roleKey.setOwnerId(user.getOwnerId());
        roleKey.setUserId(user.getUserId());
        userRoleMapper.insertSelective(roleKey);
        /**
         * 步骤3 增加组织角色表
         * */
        SysUnitRoleKey unitRole = new SysUnitRoleKey();
        unitRole.setRoleId(role.getRoleId());
        unitRole.setUnitId(user.getOwnerId());
        unitRoleMapper.insertSelective(unitRole);

        /**
         * 步骤4增加组织角色分配表
         * */
        SysUnitPa pa = new SysUnitPa();
        pa.setUnitId(user.getOwnerId());
        pa.setRoleId(role.getRoleId());
        pa.setOprId(user.getUserId());
        pa.setGrRv("G");
        unitPaMapper.insertSelective(pa);
        /**
         * 步骤5增加用户权限分配表
         * */
        SysUserPa userPa = new SysUserPa();
        userPa.setGrRv("G");
        userPa.setUserId(user.getUserId());
        userPa.setOwnerId(user.getOwnerId());
        userPa.setUnitId(user.getOwnerId());
        userPa.setRoleId(role.getRoleId());
        userPa.setOprId(user.getUserId());
        userPa.setUnlimited("T");
        userPa.setUgId(-1L);
        userPaMapper.insertSelective(userPa);
        /**
         * 1.增加角色表
         * */
        return roleMapper.insertRole(role);

    }

    /**
     * 修改权限
     */
    @Override
    public int upadteRole(SysRole role) {
        return roleMapper.updateByRole(role);
    }

    /**
     * 删除角色
     * 1.先删除角色
     * 2.删除角色对应的权限
     * 3.删除角色对应的组织
     * 4.修改用户角色分配表(打标)
     * 5.修改组织角色分配表打标
     * 6.删除角色范围表所有角色
     * 7.删除角色对应用户
     */
    @Override
    public void deleteRole(SysRole role, SysUser user) {
        //1.删除角色
        roleMapper.deleteRoleId(role);
        //2.删除角色对应的权限
        roleMapper.deleteRolePriv(role);
        SysUnitRoleKey key = new SysUnitRoleKey();
        key.setRoleId(role.getRoleId());
        key.setUnitId(user.getOwnerId());
        //3.删除组织对应的角色
        unitRoleMapper.deleteByPrimaryKey(key);
        //4.修改用户角色分配表(打标)
        SysUserPa userPa = new SysUserPa();
        userPa.setUserId(user.getUserId());
        userPa.setOwnerId(user.getOwnerId());
        for (SysUserPa pa : userPaMapper.selectAll(userPa)) {
            pa.setGrRv("R");
            userPaMapper.updateByPrimaryKeySelective(pa);
        }
        // 5.修改组织角色分配表打标
        SysUnitPa unitPa = new SysUnitPa();
        unitPa.setUnitId(user.getOwnerId());
        unitPa.setRoleId(role.getRoleId());
        List<SysUnitPa> unitPas = unitPaMapper.selectAll(unitPa);
        for (SysUnitPa pa : unitPas) {
            pa.setGrRv("R");
            unitPaMapper.updateByPrimaryKeySelective(pa);
        }
        // 6.删除角色对应角色范围表
        roleScopeMapper.deletRoleScope(role);
        // 7.删除角色对应用户
        userRoleMapper.deletUserRole(role);
    }

    /**
     * 查看角色权限
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> selectByRoleIdPriv(SysRole role) {

        List<SysPrivilege> sysPriv = privilegeMapper.selectRolePriv(role);

        return sysPriv;

    }

    /**
     * 权限对应(用户)角色
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysRole> selectPrivRoles(SysPrivilege privilege, SysUser user) {
        List<SysRole> list = new ArrayList<>();
        List<SysRole> getlist = AdminAndUser(user);

        for (SysRole role : getlist) {
            for (SysRole rolepriv : roleMapper.selectPrivRole(privilege)) {
                if (role.getRoleId().equals(rolepriv.getRoleId())) {
                    list.add(rolepriv);
                    log.info("" + rolepriv);
                }
            }
        }
        return list;
    }


    @Override
    @Transactional(readOnly = true)
    public List<SysRole> getRoleAll() {
        return roleMapper.getRoleAll();
    }

    /**
     * 修改角色权限(批量)
     */
    @Override
    public void updateRolePriv(RolePrivModel model) {
        //判断是否添加

        if (StringUtils.isNotEmpty(model.getAdd()) && model.getAdd().equals("add")) {
            if (model.getPrivadd().size() > 0) {
                for (SysPrivilege privilege : model.getPrivadd()) {
                    SysRolePrivKey record = new SysRolePrivKey();
                    record.setPrivId(privilege.getPrivId());
                    record.setRoleId(model.getRoleId());
                    rolePrivMapper.insertSelective(record);
                }
            }
        }
        //判断是否删除
        if (StringUtils.isNotEmpty(model.getDelete()) && model.getDelete().equals("delete")) {
            if (model.getPrivdelete().size() > 0) {
                for (SysPrivilege privilege : model.getPrivdelete()) {
                    SysRolePrivKey record = new SysRolePrivKey();
                    record.setPrivId(privilege.getPrivId());
                    record.setRoleId(model.getRoleId());
                    rolePrivMapper.deleteByPrimaryKey(record);
                }
            }
        }
    }


    /**
     * 增加角色权限
     */
    @Override
    public int addRolePriv(SysRole role) {
        //批量添加
        int a = 0;
        if (role.getPrivileges().size() > 0) {
            for (SysPrivilege sysPrivilege : role.getPrivileges()) {
                SysRolePrivKey roles = new SysRole();
                roles.setRoleId(role.getRoleId());
                roles.setPrivId(sysPrivilege.getPrivId());
                roleMapper.addRolePriv(roles);
                a++;
            }
        } else {
            a = roleMapper.addRolePriv(role);
        }
        return a;
    }

    /**
     * 删除角色权限
     */
    @Override
    public int deleteRolePriv(SysRole role) {
        int a = 0;
        if (role.getPrivileges().size() > 0) {
            for (SysPrivilege sysPrivilege : role.getPrivileges()) {
                SysRolePrivKey roles = new SysRole();
                roles.setRoleId(role.getRoleId());
                roles.setPrivId(sysPrivilege.getPrivId());
                roleMapper.deleteRolePriv(roles);
                a++;
            }
        } else {
            a = roleMapper.deleteRolePriv(role);
        }
        return a;
    }
}
