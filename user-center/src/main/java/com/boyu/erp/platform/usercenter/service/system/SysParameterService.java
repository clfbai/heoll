package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.github.pagehelper.PageInfo;
/**
 * 参数服务
 */
public interface SysParameterService {

     PageInfo<SysParameter> pageList(Integer pageNum, Integer pageSize, SysParameter parameter) throws ServiceException;

     SysParameter findByParameter(String parameter) throws ServiceException;

     int insertParameter(SysParameter parameter) throws ServiceException;

     int updateParameter(SysParameter parameter) throws ServiceException;




}
