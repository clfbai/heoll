package com.boyu.erp.platform.usercenter.service.collarUseservice;

import com.boyu.erp.platform.usercenter.entity.collarUse.ArqType;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: ArqDtlService
 * @description: 领用单类别
 * @author: wz
 * @create: 2019-8-23 12:19
 */
public interface ArqTypeService {

    PageInfo<ArqType> selectAll(Integer page, Integer size, ArqType arqType);

    int insertArqType(ArqType arqType, SysUser user) throws Exception;

    int updateArqType(ArqType arqType, SysUser user) throws Exception;

    int deleteArqType(ArqType arqType, SysUser user);

    /**
     * 领用单类别下拉框
     */
    List<PurKeyValue> optionGet();
}
