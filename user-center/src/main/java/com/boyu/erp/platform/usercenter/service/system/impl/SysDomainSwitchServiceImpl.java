package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysDomainSwitch;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainSwitchMapper;
import com.boyu.erp.platform.usercenter.model.SysDomainSwitchModel;
import com.boyu.erp.platform.usercenter.service.system.SysDomainSwitchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 切换领域服务
 * @author: clf
 * @create: 2019-06-04 11:20
 */
@Slf4j
@Service
@Transactional
public class SysDomainSwitchServiceImpl implements SysDomainSwitchService {
    @Autowired
    private SysDomainSwitchMapper domainSwitchMapper;


    @Override
    public int addDomainSwitchService(SysDomainSwitch sysDomainSwitch) {
        return domainSwitchMapper.insertDomainSwitch(sysDomainSwitch);
    }

    @Override
    public int deleteDomainSwitchService(SysDomainSwitch sysDomainSwitch) {
        return domainSwitchMapper.deleteByDomainSwitch(sysDomainSwitch);
    }

    @Override
    public int updateDomainSwitchService(SysDomainSwitchModel sysDomainSwitch) {
        return domainSwitchMapper.updateByDomainSwitch(sysDomainSwitch);
    }

    @Override
    public SysDomainSwitch findById(SysDomainSwitch s) {
        return domainSwitchMapper.selectByDomainSwitch(s);
    }

    /**
     * 可切换领域列表
     *
     * @param size
     * @param page
     * @param domainSwitch,
     * @param user
     * @return
     * @deprecated 查询可切换的领域列表
     */
    @Override
    public PageInfo<SysDomain> getList(Integer page, Integer size, SysDomainSwitch domainSwitch, SysUser user) {
        domainSwitch.setDomainPresentId(user.getOwnerId());
        domainSwitch.setDomainUserId(user.getUserId());
        PageHelper.startPage(page, size);
        List<SysDomain> list = domainSwitchMapper.getCutDomainList(domainSwitch);
        PageInfo<SysDomain> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }

    /**
     * @param : domainSwitch
     * @description: 查询组织已授予切换的领域列表
     * @author: CLF
     */
    @Override
    public PageInfo<SysDomain> setCutDomainList(Integer page, Integer size, SysDomainSwitch domainSwitch, SysUser user) {
        domainSwitch.setDomainAccreditId(user.getOwnerId());
        PageHelper.startPage(page, size);
        List<SysDomain> list = domainSwitchMapper.setCutDomainLis(domainSwitch);
        PageInfo<SysDomain> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
