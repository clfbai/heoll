package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;

import java.util.Set;

public interface UserScopeServer {

    boolean isAllScope(SysUser user) throws ServiceException;

    Set<UnitDomainVo> getUnitScope(SysUser user, UnitDomainVo unitDomainVo) throws ServiceException;
    Set<UnitDomainVo> getUnitClsScope(SysUser user, UnitDomainVo unitDomainVo) throws ServiceException;
    /**
     * 功能描述:  查询用户在需要过滤的组织拥有的权限
     *
     * @param vo
     * @param user
     * @return:
     * @auther: CLF
     * @date: 2019/8/10 11:03
     */
    Set<SysPrivilege> getUnitScopePriv(UnitDomainVo vo, SysUser user,String type) throws ServiceException;
}
