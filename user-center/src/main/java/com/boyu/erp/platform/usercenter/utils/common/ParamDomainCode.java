package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;

/**
 * @program: boyu-platform
 * @description: 验证领域Code是否重复
 * @author: clf
 * @create: 2019-06-03 16:42
 */
public class ParamDomainCode {


    public static boolean findDomainCode(String unitCode,SysDomainMapper domainMapper,SysUnitMapper unitMapper) {
        Integer a = domainMapper.countDomainCode(unitCode) + unitMapper.countDomainCode(unitCode);
        if (a > 0) {
            return false;
        } else {
            return true;
        }

    }
}
