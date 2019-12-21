package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysCode;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysCodeDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysCodeMapper;
import com.boyu.erp.platform.usercenter.service.system.SysCodeService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Classname SysCodeServiceImpl
 * @Description 代码接口
 * @Date 2019/5/7 17:52
 * @Created by js
 */
@Slf4j
@Service
@Transactional
public class SysCodeServiceImpl implements SysCodeService {

    @Autowired
    private SysCodeMapper sysCodeMapper;

    @Autowired
    private SysCodeDtlMapper sysCodeDtlMapper;


    @Override
    public int deleteByPrimaryKey(SysCode codeType) {
        return sysCodeMapper.deleteByPrimaryKey(codeType);
    }

    @Override
    public int insertSysCode(SysCode record) {
        return sysCodeMapper.insertSelective(record);
    }

    @Override
    @Transactional(readOnly = true)
    public SysCode getSysCode(SysCode codeType) {
        return sysCodeMapper.selectByPrimaryKey(codeType);
    }


    /**
     * 是否是打标(删除)
     */
    @Override
    public int updateSysCode(SysCode record) throws ServiceException {
        SysCode code = sysCodeMapper.selectByPrimaryKey(record);
        if (code == null) {
            throw new ServiceException("403", "检查代码类型Id是否存在: " + record.getCodeType());
        }
        BeanUtils.copyProperties(record,code);
        if (record.getIsDel().equals(CommonFainl.FAILSTATUS)) {
            SysCodeDtl codedtl = new SysCodeDtl();
            codedtl.setCodeType(record.getCodeType());
            for (SysCodeDtl dtl : sysCodeDtlMapper.selectAll(codedtl)) {
                dtl.setIsDel(CommonFainl.FAILSTATUS);
                dtl.setUpdateTime(new Date());
                dtl.setUpdateBy(record.getUpdateBy());
                sysCodeDtlMapper.updateByPrimaryKeySelective(dtl);
            }
        }
        return sysCodeMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @program: code, page, size
     * @description: 查询代码分页
     * @author: CLF
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysCode> selectAll(Integer page, Integer size, SysCode code) {
        PageHelper.startPage(page, size);
        List<SysCode> sysCodes = sysCodeMapper.selectAll(code);
        PageInfo<SysCode> pageInfo = new PageInfo<>(sysCodes);
        return pageInfo;
    }
}
