package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnType;
import com.boyu.erp.platform.usercenter.mapper.warehouse.TfnTypeMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnTypeService;
import com.boyu.erp.platform.usercenter.utils.common.BaseDateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 调拨单服务
 * @author: clf
 * @create: 2019-06-25 09:51
 */
@Service
@Transactional
public class TfnTypeServiceImpl implements TfnTypeService {
    @Autowired
    private TfnTypeMapper tfnTypeMapper;


    public List<TfnType> getAll(TfnType t) throws Exception {
        return tfnTypeMapper.getAll(t);
    }

    /**
     * 查询所有调拨单
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<TfnType> getAll(TfnType t, Integer page, Integer size) throws Exception {

        PageHelper.startPage(page, size);
        List<TfnType> resultList = tfnTypeMapper.getAll(t);
        PageInfo<TfnType>pageInfo = new PageInfo<TfnType>(resultList);
        return pageInfo;
    }

    /**
     * 增加调拨单
     */
    @Override
    public int insertTfnType(TfnType t, SysUser u) throws Exception {
        BaseDateUtils.setBeasDate(t, u, "Add");
        return tfnTypeMapper.insertTfnType(t);
    }

    /**
     * 修改调拨单
     */
    @Override
    public int updateTfnType(TfnType t, SysUser u) throws Exception {
        BaseDateUtils.setBeasDate(t, u, "Update");
        return tfnTypeMapper.updateByTfnType(t);
    }

    /**
     * 删除调拨单(打标)
     */
    @Override
    public int deleteTfnType(TfnType t, SysUser u) throws Exception {
        BaseDateUtils.setBeasDate(t, u, "Delete");
        return tfnTypeMapper.updateByTfnType(t);
    }

}


