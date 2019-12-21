package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.SpecScpDtlKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.SpecScpDtlMapper;
import com.boyu.erp.platform.usercenter.service.system.SpecScpDtlService;
import com.boyu.erp.platform.usercenter.vo.SpecScpDtlVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * 规格范围明细
 */
@Service
@Transactional
public class SpecScpDtlServiceImpl implements SpecScpDtlService {

    @Autowired
    private SpecScpDtlMapper specScpDtlMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SpecScpDtlVo> selectAll(SpecScpDtlKey specScpDtlKey) throws ServiceException {
        if (StringUtils.isBlank(specScpDtlKey.getSpecScpId()))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"规格范围ID不允许为空");
        }
        List<SpecScpDtlVo> list = specScpDtlMapper.selectAll(specScpDtlKey);
        return list;
    }

    @Override
    public int insertSpecScpDtlKey(SpecScpDtlKey specScpDtlKey) throws ServiceException {
        return specScpDtlMapper.insertSpecScpDtlKey(specScpDtlKey);
    }

    @Override
    public int updateSpecScpDtlKey(SpecScpDtlKey specScpDtlKey) throws ServiceException {
        return specScpDtlMapper.updateSpecScpDtlKey(specScpDtlKey);
    }

    @Override
    public int deleteSpecScpDtlKey(SpecScpDtlKey specScpDtlKey) throws ServiceException
    {
        if (StringUtils.isBlank(specScpDtlKey.getSpecScpId()))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"规格范围ID不允许为空");
        }
        return specScpDtlMapper.deleteSpecScpDtlKey(specScpDtlKey);
    }
}