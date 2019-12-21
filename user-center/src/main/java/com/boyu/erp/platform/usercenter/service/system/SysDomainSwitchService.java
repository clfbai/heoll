package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysDomainSwitch;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.SysDomainSwitchModel;
import com.github.pagehelper.PageInfo;

/**
 * @program: boyu-platform
 * @description: 切换领域接口
 * @author: clf
 * @create: 2019-06-04 11:17
 */
public interface SysDomainSwitchService {

    int addDomainSwitchService(SysDomainSwitch sysDomainSwitch);

    int deleteDomainSwitchService(SysDomainSwitch sysDomainSwitch);

    int updateDomainSwitchService(SysDomainSwitchModel sysDomainSwitch);

    SysDomainSwitch findById(SysDomainSwitch s);

    /**
     * 可切换领域列表
     */
    PageInfo<SysDomain> getList(Integer page, Integer size, SysDomainSwitch domainSwitch, SysUser user);

    /**
     * @param : domainSwitch
     * @description: 查询组织已授予切换的领域列表
     * @author: CLF
     */
    PageInfo<SysDomain> setCutDomainList(Integer page, Integer size, SysDomainSwitch domainSwitch, SysUser user);
}
