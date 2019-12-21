package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.Tfn;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnBxi;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.TfnBxiMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.TfnDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.TfnMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnService;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 调拨单接口实现
 * @author: ck
 * @create: 2019-06-20 15:36
 */
@Slf4j
@Service
@Transactional
public class TfnServiceImpl implements TfnService {

    @Autowired
    private TfnMapper tfnMapper;

    @Autowired
    private TfnDtlMapper tfnDtlMapper;

    @Autowired
    private TfnBxiMapper tfnBxiMapper;

    /**
     * 获取调拨单列表
     * @param page
     * @param size
     * @param tfn
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<TfnVo> getTfnList(Integer page, Integer size, Tfn tfn) throws ServiceException {
        PageHelper.startPage(page, size);
        List<TfnVo> list = tfnMapper.getTfnList(tfn);
        PageInfo<TfnVo> pageInfo = new PageInfo<TfnVo>(list);
        return pageInfo;
    }

    /**
     * 根据主键查询调拨单信息
     * @param tfn
     * @return
     * @throws ServiceException
     */
    @Override
    public TfnVo getTfnVoByPk(Tfn tfn) throws ServiceException {
        return tfnMapper.getTfnVoByPk(tfn);
    }

    /**
     * s删除调拨单信息
     * @param tfn
     * @return
     * @throws ServiceException
     */
    @Override
    public int delete(Tfn tfn) throws ServiceException {
        TfnDtl tfnDtl = new TfnDtl();
        tfnDtl.setUnitId(tfn.getUnitId());
        tfnDtl.setTfnNum(tfn.getTfnNum());
        tfnDtlMapper.deleteByPrimaryKey(tfnDtl);
        TfnBxi tfnBxi = new TfnBxi();
        tfnBxi.setUnitId(tfn.getUnitId());
        tfnBxi.setTfnNum(tfn.getTfnNum());
        tfnBxiMapper.delete(tfnBxi);
        return tfnMapper.deleteByPrimaryKey(tfn);
    }

    /**
     * 创建调拨单
     * @param tfn
     * @return
     * @throws ServiceException
     */
    @Override
    public int insert(Tfn tfn) throws ServiceException {
        return tfnMapper.insert(tfn);
    }

    /**
     * 更新调拨单
     * @param tfn
     * @return
     * @throws ServiceException
     */
    @Override
    public int update(Tfn tfn) throws ServiceException  {
        return tfnMapper.update(tfn);
    }

    /**
     * 获取当前组织最大调拨单编号
     * @param record
     * @return
     * @throws ServiceException
     */
    @Override
    public String getMaxTfnNum(Tfn record) throws ServiceException {
        return tfnMapper.getMaxTfnNum(record);
    }

    /**
     * 根据主键获取调拨单信息
     * @param record
     * @return
     * @throws ServiceException
     */
    @Override
    public Tfn getTfnByPk(Tfn record) throws ServiceException {
        return tfnMapper.getTfnByPk(record);
    }
}
