package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserPa;
import com.boyu.erp.platform.usercenter.entity.system.SysUserRoleKey;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserPaMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserRoleMapper;
import com.boyu.erp.platform.usercenter.model.system.UserRoleModel;
import com.boyu.erp.platform.usercenter.service.system.SysUserRoleService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.system.UserRoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPaMapper userPaMapper;
    @Autowired
    private UsersService usersService;

    /**
     * 查询用户角色列表
     *
     * @param page
     * @param size
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<SysRole> selectAll(Integer page, Integer size, UserRoleVo userRoleVo) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userRoleVo, user);
        //组织管理员
        if (usersService.getAdmin(user) != null) {
            //系统管理员
            if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1) {
                PageHelper.startPage(page, size);
                List<SysRole> sysUserRoleKeys = userRoleMapper.getRole(userRoleVo);
                PageInfo<SysRole> pageInfo = new PageInfo<>(sysUserRoleKeys);
                return pageInfo;
            }
            PageHelper.startPage(page, size);
            List<SysRole> sysUserRoleKeys = userRoleMapper.adminRole(userRoleVo);
            PageInfo<SysRole> pageInfo = new PageInfo<>(sysUserRoleKeys);
            return pageInfo;
        }

        //普通用户
        PageHelper.startPage(page, size);
        List<SysRole> sysUserRoleKeys = userRoleMapper.selectAll(userRoleVo);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysUserRoleKeys);
        return pageInfo;
    }

    /**
     * 添加用户角色
     * 同时需要添加用户角色分配表数据
     */
    @Override
    public int insert(UserRoleModel f, SysUser user) {
        for (SysUserRoleKey record : f.getAddRoleKeys()) {
            Long ugId = -1L;
            //判断是否添加分组ID
            if (record.getUnitId() != null && record.getUnitId() != 0L) {
                record.setUgId(ugId);
            } else {
                record.setUnitId(0L);
            }
            SysUserPa pa = accreditRecycle(record, user);
            List<SysUserPa> sysUserPas = userPaMapper.selectAll(pa);
            //已有数据
            if (sysUserPas != null && sysUserPas.size() > 0) {
                pa.setGrRv("G");
                userPaMapper.accreditRecycle(pa);//重新授权
            } else {
                pa.setGrRv("G");
                userPaMapper.insertSelective(pa);//添加
            }

        }
        return userRoleMapper.insertList(f.getAddRoleKeys());
    }


    /**
     * 批量删除用户角色
     * 同时需要删除用户角色分配表数据
     */
    @Override
    public int deleteByPrimaryKey(UserRoleModel model, SysUser user) {
        for (SysUserRoleKey key : model.getDeleteRoleKeys()) {
            userPaMapper.accreditRecycle(accreditRecycle(key, user));
            if (key.getUnitId() != null && key.getUnitId() != 0L) {
                key.setUgId(null);
            } else {
                key.setUnitId(null);
            }
        }

        return userRoleMapper.deleteList(model.getDeleteRoleKeys());
    }

    /**
     * 功能描述:  批量修改用户角色
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 19:23
     */
    @Override
    public void updateUserRole(UserRoleModel userRole, SysUser user) {
        //增加
        if (userRole.getAddRoleKeys() != null && userRole.getAddRoleKeys().size() > 0) {
            this.insert(userRole, user);
        }
        //删除
        if (userRole.getDeleteRoleKeys() != null && userRole.getDeleteRoleKeys().size() > 0) {
            this.deleteByPrimaryKey(userRole, user);
        }
    }

    /**
     * 授权回收
     *
     * @param key
     * @param user
     * @return
     */
    private SysUserPa accreditRecycle(SysUserRoleKey key, SysUser user) {
        SysUserPa userPa = new SysUserPa();
        userPa.setUserId(key.getUserId());
        userPa.setOwnerId(key.getOwnerId());
        userPa.setGrRv("R");
        userPa.setUnlimited("T");
        userPa.setUgId(key.getUgId());
        userPa.setUnitId(key.getUnitId());
        userPa.setRoleId(key.getRoleId());
        userPa.setOprId(user.getUserId());
        userPa.setOpTime(new Date());
        return userPa;
    }
}
