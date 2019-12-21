package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.SpecScp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.SpecGrpMapper;
import com.boyu.erp.platform.usercenter.mapper.SpecScpMapper;
import com.boyu.erp.platform.usercenter.service.system.SpecScpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecScpServiceImpl implements SpecScpService {

    @Autowired
    private SpecScpMapper specScpMapper;

    @Autowired
    private SpecGrpMapper specGrpMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<SpecScp> selectAll(Integer page, Integer size, SpecScp specScp) {
        PageHelper.startPage(page, size);
        List<SpecScp> specScps = specScpMapper.selectAll(specScp);
        PageInfo<SpecScp> pageInfo = new PageInfo<SpecScp>(specScps);
        return pageInfo;
    }

    /**
     * 下拉框选择所有的规格
     *
     * @return
     * @throws ServiceException
     */
    @Override
    public List<SpecScp> getSelectAll() throws ServiceException {
        SpecScp specScp = new SpecScp();
        return specScpMapper.selectAll(specScp);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecScp getSpecScpById(SpecScp specScpId) {
        return specScpMapper.selectByPrimaryKey(specScpId);
    }

    @Override
    public int insertSpecScp(SpecScp specScp) throws ServiceException {
        return specScpMapper.insertSpecScp(specScp);
    }

    @Override
    public int updateSpecScp(SpecScp specScp) throws ServiceException {
        return specScpMapper.updateSpecScp(specScp);
    }

    @Override
    public int deleteSpecScp(SpecScp specScp) throws ServiceException {
        return specScpMapper.deleteSpecScp(specScp);
    }

    /**
     * 查询规格范围下拉
     */
    @Override
    public List<SpecScp> getSelectOption() {
        return specScpMapper.selectOption();
    }
}