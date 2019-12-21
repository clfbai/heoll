package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.SpecGrp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.SpecGrpMapper;
import com.boyu.erp.platform.usercenter.service.system.SpecGrpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecGrpServiceImpl implements SpecGrpService {

    @Autowired
    private SpecGrpMapper specGrpMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<SpecGrp> selectAll(Integer page, Integer size, SpecGrp specGrp) throws ServiceException {
        PageHelper.startPage(page,size);
        List<SpecGrp> specGrps = specGrpMapper.selectAll(specGrp);
        PageInfo<SpecGrp> pageInfo=new PageInfo<SpecGrp>(specGrps);
        return pageInfo;
    }

    @Override
    public List<SpecGrp> getSelectAll() throws ServiceException {
        SpecGrp specGrp=new SpecGrp();
        List<SpecGrp> resultList= this.specGrpMapper.selectAll(specGrp);
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public SpecGrp getSpecGrpId(SpecGrp specGrp) throws ServiceException{
        return specGrpMapper.selectByPrimaryKey(specGrp);
    }

    @Override
    public int insertSpecGrp(SpecGrp specGrp) throws ServiceException {
        return specGrpMapper.insertSpecGrp(specGrp);
    }

    @Override
    public int updateSpecGrp(SpecGrp specGrp) throws ServiceException{

        if(StringUtils.isBlank(specGrp.getSpecGrpId()))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"规格组ID为空");
        }
        return specGrpMapper.updateSpecGrp(specGrp);
    }

    @Override
    public int deleteSpecGrp(SpecGrp specGrp) throws ServiceException{
        return specGrpMapper.deleteSpecGrp(specGrp);
    }
    /**
     * 通过规格差规格组名称
     */
    @Override
    public SpecGrp getSpecAndSpecGrp(Spec spec) throws ServiceException {
        return specGrpMapper.getSpecAndSpecGrp(spec);
    }
}