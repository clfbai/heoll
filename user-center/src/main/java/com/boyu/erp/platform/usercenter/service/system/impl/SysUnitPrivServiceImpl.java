package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.service.system.SysUnitPrivService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivModel;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysUnitPrivServiceImpl implements SysUnitPrivService {

    @Autowired
    private SysUnitPrivMapper unitPrivMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUnitPaMapper unitPaMapper;

    @Autowired
    private SysUnitRoleMapper unitRoleMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysRoleScopeMapper roleScopeMapper;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysUserPrivMapper userPrivMapper;
    @Autowired
    private SysUserPaMapper userPaMapper;

    /**
     * 添加组织权限
     * 添加到组织权限表的同时也要插入到组织权限分配表
     * 组织权限分配表包含组织所拥有的所有角色及权限
     */
    @Override
    public int insertSelective(List<SysUnitPrivKey> record, SysUser user) {
        int count = 0;
        for (SysUnitPrivKey unitPriv : record) {
            SysUnitPa unitPa = new SysUnitPa();
            unitPa.setUnitId(unitPriv.getUnitId());//组织id
            unitPa.setPrivId(unitPriv.getPrivId());//权限id

            unitPrivMapper.insertSelective(unitPriv);

            List<SysUnitPa> sysUnitPas = unitPaMapper.selectAll(unitPa);
            unitPa.setGrRv("G");//授权回收
            unitPa.setOprId(user.getUserId()); //操作员
            unitPa.setOpTime(new Date());//操作时间
            if (sysUnitPas == null || sysUnitPas.size() == 0) {
                unitPaMapper.insertSelective(unitPa);
            } else {
                unitPaMapper.updateByPrimaryKeySelective(unitPa);
            }
            //无需添加组织权限
            SysUser adminUser = unitMapper.selectByunitAdmin(unitPriv.getUnitId());
            SysPrivilege privilege = new SysPrivilege();
            //privilege.setPrivId(unitPriv.getPrivId());
            // addAdminPriv(privilege, adminUser);


            count++;
        }
        return count;
    }

    /**
     * 查询组织权限
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysPrivilege> selectAll(Integer page, Integer size, UnitPrivVo unitPrivVo, SysUser user) {

        if (unitPrivVo.getUnitId() == null) {
            unitPrivVo.setUnitId(user.getOwnerId());
            if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1) {
                PageHelper.startPage(page, size);
                List<SysPrivilege> list = unitPrivMapper.getByAll(unitPrivVo);
                PageInfo<SysPrivilege> pageInfo = new PageInfo<SysPrivilege>(list);
                return pageInfo;
            }
            PageHelper.startPage(page, size);
            List<SysPrivilege> list = unitPrivMapper.selectAll(unitPrivVo);
            PageInfo<SysPrivilege> pageInfo = new PageInfo<SysPrivilege>(list);
            return pageInfo;
        } else {
            if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1 && unitPrivVo.getUnitId().equals(user.getOwnerId())) {
                PageHelper.startPage(page, size);
                unitPrivVo.setUnitId(user.getOwnerId());
                List<SysPrivilege> list = unitPrivMapper.getByAll(unitPrivVo);
                PageInfo<SysPrivilege> pageInfo = new PageInfo<SysPrivilege>(list);
                return pageInfo;
            } else {
                PageHelper.startPage(page, size);
                List<SysPrivilege> list = unitPrivMapper.selectAll(unitPrivVo);
                PageInfo<SysPrivilege> pageInfo = new PageInfo<SysPrivilege>(list);
                return pageInfo;
            }
        }
    }


    /**
     * 删除组织权限
     */
    @Override
    public int deleteUnitPriv(List<SysUnitPrivKey> sysUnit, SysUser user) {
        int count = 0;
        for (SysUnitPrivKey k : sysUnit) {
            unitPrivMapper.deleteUnitPriv(k);//删除组织权限
            SysUnitPa pa = new SysUnitPa();
            pa.setUnitId(k.getUnitId());//组织id
            pa.setPrivId(k.getPrivId());//权限id
            pa.setGrRv("R");//回收授权
            pa.setOpTime(new Date());//操作时间
            pa.setOprId(user.getUserId());//操作员id
            unitPaMapper.accreditRecycle(pa);//授权回收
            SysUser adminUser = unitMapper.selectByunitAdmin(k.getUnitId());
            SysPrivilege privilege = new SysPrivilege();
            privilege.setPrivId(k.getPrivId());
            deleteAdminPriv(privilege, adminUser);
            count++;
        }
        return count;
    }

    /**
     * 1.组织权限的修改 增加或删除
     * 需要操做组织权限分配表
     * <p>
     * 2. 组织角色授予或回收需要指定角色范围
     * 角色范围 all 组织及下属组织
     * 角色范围 ol  当前组织
     * 角色范围 or 组织下多组织任选
     * <p>
     * 3.在授予组织角色时 需要判断 角色范围是否超出组织范围
     * 例如: THJ  下属组织 TEXT  和  TS
     * 角色 role  属于THJ   类型 all 或者 or 那么role  不能授予 TEXT 和 TS这两个组织
     * <p>
     * 4. 在回收角色时 回收角色范围
     * 即删除角色范围表对应关系
     *
     * @return
     */
    @Override
    public int updateUnitRriv(UnitPrivModel model, SysUser user) {
        int count = 0;
        /**
         * 组织权限
         */
        if (StringUtils.equals(model.getAdd(), "add")) {
            /**
             * 组织权限增加
             * 1.增加组织角色
             * 2.设定组织角色范围
             * */
            for (SysPrivilege k : model.getAddPrivilege()) {
                SysUnitPrivKey key = new SysUnitPrivKey();
                key.setPrivId(k.getPrivId());
                key.setUnitId(model.getUnitId());
                unitPrivMapper.insertSelective(key);
                SysUnitPa pa = new SysUnitPa();
                pa.setUnitId(model.getUnitId());
                pa.setPrivId(k.getPrivId());
                insertUnitPa(user, pa);//修改组织权限分配表数据
                count++;
            }
            /**
             * 1. 组织角色 增加
             * */
            if (CollectionUtils.isNotEmpty(model.getAddUnitRole())) {
                for (SysRole role : model.getAddUnitRole()) {
                    SysUnitRoleKey key = new SysUnitRoleKey();
                    key.setUnitId(model.getUnitId());
                    key.setRoleId(role.getRoleId());
                    count += unitRoleMapper.insertSelective(key);
                    SysUnitPa pa = new SysUnitPa();
                    pa.setUnitId(model.getUnitId());
                    pa.setRoleId(role.getRoleId());
                    insertUnitPa(user, pa);//修改组织权限分配表数据
                }
            }


        }
        /**
         * 删除组织权限
         */
        if (StringUtils.equals(model.getDelete(), "delete")) {
            /**
             * 组织权限删除
             *
             * */
            for (SysPrivilege k : model.getDeletePrivilege()) {
                SysUnitPrivKey key = new SysUnitPrivKey();
                key.setPrivId(k.getPrivId());
                key.setUnitId(model.getUnitId());
                unitPrivMapper.deleteUnitPriv(key);
                SysUnitPa pa = new SysUnitPa();
                pa.setUnitId(model.getUnitId());
                pa.setPrivId(k.getPrivId());
                deleteUnitPa(user, pa);//修改组织权限分配表数据
                SysUser adminUser = unitMapper.selectByunitAdmin(model.getUnitId());
                SysPrivilege privilege = new SysPrivilege();
                privilege.setPrivId(k.getPrivId());
                deleteAdminPriv(privilege, adminUser);
                count++;
            }
            /**
             * 组织角色删除
             *
             * 1.删除组织角色
             * 2.删除组织角色范围
             * 3.删除组织下用户拥有该角色记录(user_role表)
             * 4.修改组织权限分配表数据
             * */
            if (model.getDeleteUnitRole() != null) {
                if (CollectionUtils.isNotEmpty(model.getDeleteUnitRole())) {
                    for (SysRole role : model.getDeleteUnitRole()) {
                        SysUnitRoleKey key = new SysUnitRoleKey();
                        key.setUnitId(model.getUnitId());
                        key.setRoleId(role.getRoleId());
                        /**
                         * 1.删除组织角色
                         * */
                        count += unitRoleMapper.deleteByPrimaryKey(key);
                        SysUnitPa pa = new SysUnitPa();
                        pa.setUnitId(model.getUnitId());
                        pa.setRoleId(role.getRoleId());
                        /**
                         * 4.修改组织权限分配表数据
                         * */
                        deleteUnitPa(user, pa);
                        /**
                         * 3删除组织下用户对应角色
                         * */
                        SysUserRoleKey roleKey = new SysUserRoleKey();
                        roleKey.setUnitId(model.getUnitId());
                        roleKey.setRoleId(role.getRoleId());
                        count += userRoleMapper.deleteUserRole(roleKey);
                    }
                }
            }
        }


        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysPrivilege> getUnitPrivAll(Long unitId) {
        UnitPrivVo unitPrivVo = new UnitPrivVo();
        unitPrivVo.setUnitId(unitId);
        return unitPrivMapper.selectAll(unitPrivVo);
    }

    /**
     * 功能描述: 查询组织所有权限(判断是否是系统组织)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 16:42
     */
    @Override
    public List<SysPrivilege> getUnitPriv(Long unitId) {
        if (unitMapper.getAdminUnit(unitId) != null && unitMapper.getAdminUnit(unitId).getOprId() == -1L) {
            return unitPrivMapper.getByAll(new UnitPrivVo());
        }
        UnitPrivVo vo = new UnitPrivVo();
        vo.setUnitId(unitId);
        return unitPrivMapper.selectAll(vo);
    }

    /**
     * 添加组织权限分配表数据
     */
    private void insertUnitPa(SysUser user, SysUnitPa pa) {
        pa.setGrRv("R");
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
     * 修改组织权限分配表数据
     */
    private void deleteUnitPa(SysUser user, SysUnitPa pa) {
        pa.setOpTime(new Date());
        pa.setOprId(user.getUserId());
        pa.setGrRv("R");
        unitPaMapper.accreditRecycle(pa);

    }


    public void addAdminPriv(SysPrivilege privilege, SysUser user) {
        //1. 增加用户权限分配表
        SysUserPa userPa = new SysUserPa();
        userPa.setPrivId(privilege.getPrivId());//权限Id
        userPa.setUserId(user.getUserId());
        userPa.setOwnerId(user.getOwnerId());
        userPa.setUnitId(user.getOwnerId());
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
        privKey.setUserId(user.getUserId());
        privKey.setOwnerId(user.getOwnerId());
        privKey.setPrivId(privilege.getPrivId());
        userPrivMapper.insertSelective(privKey);
    }

    public void deleteAdminPriv(SysPrivilege privilege, SysUser user) {
        //1. 修改用户权限分配表
        SysUserPa userPa = new SysUserPa();
        userPa.setPrivId(privilege.getPrivId());//权限Id
        userPa.setUserId(user.getUserId());
        userPa.setOwnerId(user.getOwnerId());
        userPa.setUnitId(user.getOwnerId());
        userPa.setUgId(-1L);//分组
        userPa.setGrRv("R");//回收权限
        userPa.setUnlimited("T");//是否全局
        userPa.setOprId(user.getUserId());//操作员Id
        userPaMapper.accreditRecycle(userPa);
        //2.删除用户权限表 对应关系
        SysUserPrivKey privKey = new SysUserPrivKey();
        privKey.setUnlimited("T");
        privKey.setOwnerId(user.getOwnerId());
        privKey.setPrivId(privilege.getPrivId());
        userPrivMapper.deleteUintPriv(privKey);
    }
}
