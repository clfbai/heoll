package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitPa;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitPaMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUnitPaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysUnitPaServiceImpl implements SysUnitPaService {

    @Autowired
    private SysUnitPaMapper unitPaMapper;



    /**
     * 添加
     */
    @Override
    public int insertSelective(SysUnitPa record) {
        return unitPaMapper.insertSelective(record);
    }

    /**
     * 查询
     */
    @Override
    @Transactional(readOnly = true)
    public SysUnitPa selectByPrimaryKey(long unitPaId) {
        return unitPaMapper.selectByPrimaryKey(unitPaId);
    }


    /**
     * 修改
     */
    @Override
    public int updateByPrimaryKeySelective(SysUnitPa record) {
        return unitPaMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 分页查询
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysUnitPa> selectAll(Integer page, Integer size, SysUnitPa sysUnitPa) {
        PageHelper.startPage(page,size);
        List<SysUnitPa> sysUnitPas = unitPaMapper.selectAll(sysUnitPa);
        PageInfo<SysUnitPa> pageInfo=new PageInfo(sysUnitPas);

        return pageInfo;
    }
}
