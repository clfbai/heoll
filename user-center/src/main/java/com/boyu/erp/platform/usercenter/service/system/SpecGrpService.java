package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.SpecGrp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 规格组
 */
public interface SpecGrpService {

    public PageInfo<SpecGrp> selectAll(Integer page, Integer size, SpecGrp specGrp) throws ServiceException;

    public SpecGrp getSpecGrpId(SpecGrp specGrp) throws ServiceException;

    public List<SpecGrp> getSelectAll() throws ServiceException;

    public int insertSpecGrp(SpecGrp specGrp) throws ServiceException;

    public int updateSpecGrp(SpecGrp specGrp) throws ServiceException;

    public int deleteSpecGrp(SpecGrp specGrp) throws ServiceException;

    /**
     * 通过规格差规格组名称
     */
    SpecGrp getSpecAndSpecGrp(Spec specGrp) throws ServiceException;
}
