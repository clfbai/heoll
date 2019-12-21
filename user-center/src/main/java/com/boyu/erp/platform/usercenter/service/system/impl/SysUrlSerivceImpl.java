package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUrl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUrlMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUrlSerivce;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 路径服务
 * @author: clf
 * @create: 2019-05-31 11:42
 */
@Slf4j
@Service
@Transactional
public class SysUrlSerivceImpl implements SysUrlSerivce {
    @Autowired
    private SysUrlMapper urlMapper;


    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysUrl> pageUrl(Integer page, Integer size, SysUrl url) {
        PageHelper.startPage(page, size);
        List<SysUrl> resultList = urlMapper.findAll(url);
        PageInfo<SysUrl> pageInfo = new PageInfo<SysUrl>(resultList);
        return pageInfo;
    }

    /**
     * 增加页面路径
     */
    @Override
    public int addUrl(SysUrl url, SysUser sessionSysUser) {
        url.setCreateBy(sessionSysUser.getUserId());
        url.setCreateTime(new Date());
        url.setUpdateBy(sessionSysUser.getUserId());
        url.setUpdateTime(new Date());
        url.setIsDel(CommonFainl.BTYPESTATUS);
        return urlMapper.insertSelective(url);
    }

    /**
     * 修改页面路径
     */
    @Override
    public int updateUrl(SysUrl url, SysUser sessionSysUser) {
        url.setUpdateBy(sessionSysUser.getUserId());
        url.setUpdateTime(new Date());
        return urlMapper.updateUrl(url);
    }

    /**
     * 打标删除
     */
    @Override
    public int deleteUrl(SysUrl url, SysUser nullUser) {
        url.setUpdateBy(nullUser.getUserId());
        url.setUpdateTime(new Date());
        url.setIsDel(CommonFainl.FAILSTATUS);
        return urlMapper.updateUrl(url);
    }

    /**
     * 组件查询
     */
    @Override
    public Boolean findUrl(SysUrl url) {
        return urlMapper.findUrl(url) == null;
    }


}
