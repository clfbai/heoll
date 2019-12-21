package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitPa;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitRoleKey;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitPaMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitRoleMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUnitRoleService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.system.UnitRoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SysUnitRoleServiceImpl implements SysUnitRoleService {


    @Autowired
    private SysUnitRoleMapper unitRoleMapper;

    @Autowired
    private UsersService usersService;
    @Autowired
    private SysUnitPaMapper unitPaMapper;


    /**
     * 删除组织角色
     * 同时需要修改组织权限分配表的数据
     */
    @Override
    public int deleteByPrimaryKey(List<SysUnitRoleKey> key, SysUser user) {
        int count = 0;
        for (SysUnitRoleKey k : key) {
            unitRoleMapper.deleteByPrimaryKey(k);
            SysUnitPa pa = new SysUnitPa();
            pa.setUnitId(k.getUnitId());//组织id
            pa.setRoleId(k.getRoleId());//角色id
            pa.setGrRv("T");
            pa.setOprId(user.getUserId());//操作员id
            pa.setOpTime(new Date());
            unitPaMapper.accreditRecycle(pa);
            count++;
        }
        return count;
    }

    /**
     * 添加组织角色
     * 同时需要修改组织权限分配表的数据
     */
    @Override
    public int insertSelective(List<SysUnitRoleKey> record, SysUser user) {
        int count = 0;
        for (SysUnitRoleKey k : record) {
            unitRoleMapper.insertSelective(k);
            SysUnitPa pa = new SysUnitPa();
            pa.setUnitId(k.getUnitId());
            pa.setRoleId(k.getRoleId());

            List<SysUnitPa> sysUnitPas = unitPaMapper.selectAll(pa);
            pa.setGrRv("G");
            pa.setOprId(user.getUserId());//操作员id
            pa.setOpTime(new Date());
            //已有数据 修改
            if (sysUnitPas != null && sysUnitPas.size() > 0) {
                unitPaMapper.accreditRecycle(pa);//重新授权
            } else {
                unitPaMapper.insertSelective(pa);//添加新授权
            }

            count++;
        }

        return count;
    }


    /**
     * 角色条件查询分页
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysRole> getList(Integer page, Integer size, UnitRoleVo key, SysUser user) {
        //不传组织Id 为查询当前用户组织角色
        if (key.getUnitId() == null) {
            key.setUnitId(user.getOwnerId());
            //判断当前用户是否是系统管理员
            if (usersService.getIsAdmin(user)) {
                key.setUnitId(null);
                PageHelper.startPage(page, size);
                List<SysRole> sysUnitRoleKeys = unitRoleMapper.getAll(key);
                PageInfo<SysRole> pageInfo = new PageInfo<>(sysUnitRoleKeys);
                return pageInfo;
            }
            PageHelper.startPage(page, size);
            List<SysRole> sysUnitRoleKeys = unitRoleMapper.selectAll(key);
            PageInfo<SysRole> pageInfo = new PageInfo<>(sysUnitRoleKeys);
            return pageInfo;
        } else {
            //传组织Id
            /**
             * 1.判断传入组织是否为系统最大组织
             * 2.是否是查询当前用户所在组织
             * */
            log.info("=====" + key.getUnitId());
            if (usersService.getIsAdmin(user) && key.getUnitId().equals(user.getOwnerId())) {
                /**
                 * 查询系统管理员组织角色
                 * */
                PageHelper.startPage(page, size);
                key.setUnitId(user.getOwnerId());
                List<SysRole> sysUnitRoleKeys = unitRoleMapper.getAll(key);
                PageInfo<SysRole> pageInfo = new PageInfo<>(sysUnitRoleKeys);
                return pageInfo;
            } else {
                /**
                 * 查询其他组织角色
                 * */
                PageHelper.startPage(page, size);
                List<SysRole> sysUnitRoleKeys = unitRoleMapper.selectAll(key);
                PageInfo<SysRole> pageInfo = new PageInfo<>(sysUnitRoleKeys);
                return pageInfo;
            }

        }
    }

    /**
     * 查询组织拥有角色
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysUnitRoleKey> findByUnitRole(Long unitId) {
        return unitRoleMapper.findByUnitId(unitId);
    }
}
