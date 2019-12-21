package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysDomainSwitch;
import com.boyu.erp.platform.usercenter.model.SysDomainSwitchModel;

import java.util.List;

public interface SysDomainSwitchMapper {
    int deleteByDomainSwitch(SysDomainSwitch key);

    int insertDomainSwitch(SysDomainSwitch record);

    SysDomainSwitch selectByDomainSwitch(SysDomainSwitch key);

    int updateByDomainSwitch(SysDomainSwitchModel record);

    /**
     * 查询可切换的领域列表
     */
    List<SysDomain> getCutDomainList(SysDomainSwitch domainSwitch);

    List<SysDomain> setCutDomainLis(SysDomainSwitch domainSwitch);

    List<SysDomain> statusDomain(SysDomainSwitch domain);
}