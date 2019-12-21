package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SysParameterImpl implements SysParameterService {

    @Autowired
    private SysParameterMapper sysParameterMapper;

    /**
     *@program: parameter
     *@description: 查询指定参数
     *@author: CLF
     *@create: 2019-5-18 09:31
     */
    @Override
    @Transactional(readOnly = true)
    public SysParameter findByParameter(String parameter) {
        return sysParameterMapper.findById(parameter);
    }

    /**
     * 模糊查询分页
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysParameter> pageList(Integer page, Integer size, SysParameter parameter) {
        PageHelper.startPage(page, size);
        List<SysParameter> list = sysParameterMapper.selectByLikePrimary(parameter);
        PageInfo<SysParameter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 增加参数
     */
    @Override
    public int insertParameter(SysParameter parameter) {

        return sysParameterMapper.insertSelective(parameter);
    }

    /**
     * 修改参数
     */
    @Override
    public int updateParameter(SysParameter parameter) {

        return sysParameterMapper.updateByPrimaryKeySelective(parameter);
    }
}
