package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUserPa;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserPaMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUserPaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysUserPaServiceImpl implements SysUserPaService {

    @Autowired
    private SysUserPaMapper userPaMapper;

    /**
     * 添加数据
     */
    @Override
    public int insertSelective(SysUserPa record) {
        return userPaMapper.insertSelective(record);
    }

    /**
     * 主键查询
     */
    @Override
    @Transactional(readOnly = true)
    public SysUserPa selectByPrimaryKey(String userPaId) {
        return userPaMapper.selectByPrimaryKey(userPaId);
    }

    /**
     * 修改数据
     */
    @Override
    public int updateByPrimaryKeySelective(SysUserPa record) {
        return userPaMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 条件分页查询
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysUserPa> selectAll(Integer page, Integer size,SysUserPa userPa) {
        PageHelper.startPage(page,size);
        List<SysUserPa> sysUserPas = userPaMapper.selectAll(userPa);
        PageInfo<SysUserPa> paPageInfo=new PageInfo<>(sysUserPas);
        return paPageInfo;
    }
}
