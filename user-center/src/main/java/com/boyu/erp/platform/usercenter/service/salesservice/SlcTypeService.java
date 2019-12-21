package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 销售合同接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface SlcTypeService {

    public int deleteBySlcType(SlcType slcType,SysUser user) throws ServiceException;

    public int insert(SlcType slcType, SysUser user) throws Exception;

    public int updateBySlcType(SlcType slcType, SysUser user) throws Exception;

    public PageInfo<SlcType> getSlcTypeList(Integer page, Integer size, SlcType slcType) throws ServiceException;

    public List<PurKeyValue> optionGet();

    /**
     * 通过选取的类别查询出相关数据
     */
    PscAutoVo selectByPscAuto(String slcType);
}
