package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.SpecScpDtlKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.SpecScpDtlVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 规格范围明细
 */
public interface SpecScpDtlService {

    public List<SpecScpDtlVo> selectAll(SpecScpDtlKey specScpDtlKey) throws ServiceException;

    public int insertSpecScpDtlKey(SpecScpDtlKey specScpDtlKey) throws ServiceException;

    public int updateSpecScpDtlKey(SpecScpDtlKey specScpDtlKey) throws ServiceException;

    public int deleteSpecScpDtlKey(SpecScpDtlKey specScpDtlKey) throws ServiceException;
}
