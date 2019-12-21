package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.sales.SrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcAutoVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 退销合同类别接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface SrcTypeService {

    public int deleteBySrcType(SrcType srcType, SysUser user) throws ServiceException;

    public int insert(SrcType srcType, SysUser user) throws Exception;

    public int updateBySrcType(SrcType srcType, SysUser user) throws Exception;

    public PageInfo<SrcType> getSrcTypeList(Integer page, Integer size, SrcType srcType) throws ServiceException;

    public List<SrcType> optionGet();

    /**
     * 退购合同下拉框
     */
    List<PurKeyValue> purGet();

    /**
     * 类别相关数据查询
     *
     * @return
     */
    RtcAutoVo selectByRtcAuto(String srcType);
}
