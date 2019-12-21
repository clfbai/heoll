package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.SpecScp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 规格范围
 */
public interface SpecScpService {

    public PageInfo<SpecScp> selectAll(Integer page, Integer size,SpecScp specScp) throws ServiceException;

    public List<SpecScp> getSelectAll() throws ServiceException;

    public SpecScp getSpecScpById(SpecScp specScp) throws ServiceException;

    public int insertSpecScp(SpecScp specScp) throws ServiceException;

    public int updateSpecScp(SpecScp specScp) throws ServiceException;

    public int deleteSpecScp(SpecScp specScp) throws ServiceException;

    List<SpecScp> getSelectOption();
}
