package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysFunction;
import com.boyu.erp.platform.usercenter.entity.system.SysMenuDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysFunctionMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysMenuDtlMapper;
import com.boyu.erp.platform.usercenter.service.system.SysFunctionSerivce;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SysFunctionSerivceImpl implements SysFunctionSerivce {

    @Autowired
    private SysFunctionMapper functionMapper;
    @Autowired
    private SysMenuDtlMapper menuDtlMapper;
    @Autowired
    private UsersService usersService;


    /**
     * 功能菜单查询
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysFunction> findAll(Integer page, Integer size, SysFunction function, SysUser user) {
        log.info("{}" + functionMapper.findAll(function));
        function.setOwnerId(user.getOwnerId());
        function.setUserId(user.getUserId());
        if (usersService.getAdmin(user) != null) {
            if (usersService.getAdmin(user).getOprId() == -1) {
                PageHelper.startPage(page, size);
                List<SysFunction> resultList = functionMapper.findAll(function);
                PageInfo<SysFunction> pageInfo = new PageInfo<SysFunction>(resultList);
                log.info("admin----------------------->" + function);
                log.info("resultList____________________>    " + resultList.size());
                return pageInfo;
            }
            PageHelper.startPage(page, size);
            List<SysFunction> resultList = functionMapper.findByUnit(function);
            PageInfo<SysFunction> pageInfo = new PageInfo<SysFunction>(resultList);
            log.info("Noadmin=============================>" + function);
            log.info("resultList===============================>    " + resultList.size());
            return pageInfo;

        }
        PageHelper.startPage(page, size);
        List<SysFunction> resultList = functionMapper.getUserTree(function);
        PageInfo<SysFunction> pageInfo = new PageInfo<SysFunction>(resultList);
        return pageInfo;
    }

    /**
     * 添加功能
     */
    @Override
    public int addFunction(SysFunction function) {
        return functionMapper.insertSelective(function);
    }

    /**
     * 修改功能
     */
    @Override
    public int updateFunction(SysFunction function) {
        return functionMapper.updateByPrimaryKeySelective(function);
    }

    /**
     * （慎用）
     * 删除功能
     * 1.删除功能表
     * 2.删除目录详情表
     */
    @Override
    public void deleteFunction(SysFunction function) {
        functionMapper.deleteByPrimaryKey(function.getFuncId());
        SysMenuDtl dtl = new SysMenuDtl();
        dtl.setFuncId(function.getFuncId());
        menuDtlMapper.deleteByPrimaryKey(dtl);
    }
}
