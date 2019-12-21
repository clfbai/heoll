package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivDepKey;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysPrivDepMapper;
import com.boyu.erp.platform.usercenter.model.PrivdelModel;
import com.boyu.erp.platform.usercenter.service.system.PrivDelSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 权限依赖服务
 * @author: clf
 * @create: 2019-05-27 09:34
 */
@Slf4j
@Service
@Transactional
public class PrivDelSerivceImpl implements PrivDelSerivce {
    @Autowired
    private SysPrivDepMapper privDepMapper;

    /**
     * 通过权限Id 查询权限依赖
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysPrivDepKey> selectPrivDelKey(SysPrivDepKey key) {
        return privDepMapper.findByPrivId(key);
    }

    /**
     * 通过权限Id和依赖权限Id查询
     */
    @Override
    @Transactional(readOnly = true)
    public SysPrivDepKey findByPrivDel(SysPrivDepKey depKey) {
        return privDepMapper.findByPrivDel(depKey);
    }

    /**
     * 添加依赖权限
     */
    @Override
    public int addPrivDel(SysPrivDepKey depKey) {
        return privDepMapper.insert(depKey);
    }

    /**
     * 删除依赖权限
     */
    @Override
    public int deletePrivDel(SysPrivDepKey depKey) {
        return privDepMapper.deleteByPrimaryKey(depKey);
    }

    /**
     * 修改依赖权限
     */
    @Override
    public int updatePrivDel(PrivdelModel depKey) {
        return privDepMapper.updatePrivDel(depKey);
    }


}
