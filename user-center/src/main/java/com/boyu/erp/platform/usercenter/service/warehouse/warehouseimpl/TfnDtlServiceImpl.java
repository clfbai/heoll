package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.Tfn;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.TfnDtlMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnDtlService;
import com.boyu.erp.platform.usercenter.utils.warehouse.TfnUtil;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnDtlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 调拨单明细接口实现
 * @author: ck
 * @create: 2019-06-28 15:36
 */
@Slf4j
@Service
@Transactional
public class TfnDtlServiceImpl implements TfnDtlService {

    @Autowired
    private TfnDtlMapper tfnDtlMapper;

    @Autowired
    private TfnUtil tfnUtil;

    /**
     * 获取调拨单明细列表
     * @param page
     * @param size
     * @param tfnDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<TfnDtlVo> getTfnDtlList(Integer page, Integer size, TfnDtl tfnDtl) throws ServiceException {
        PageHelper.startPage(page, size);
        List<TfnDtlVo> list = tfnDtlMapper.getTfnDtlList(tfnDtl);
        PageInfo<TfnDtlVo> pageInfo = new PageInfo<TfnDtlVo>(list);
        return pageInfo;
    }

    /**
     * 创建调拨单明细
     * @param tfnDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public int insert(TfnDtl tfnDtl) throws ServiceException {
        int i = tfnDtlMapper.insert(tfnDtl);
        Tfn tfn = new Tfn();
        tfn.setUnitId(tfnDtl.getUnitId());
        tfn.setTfnNum(tfnDtl.getTfnNum());
        tfnUtil.reCalTfnInfo(tfn);
        return i;
    }

    /**
     * g更新调拨单明细
     * @param tfnDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public int update(TfnDtl tfnDtl) throws ServiceException {
        int i = tfnDtlMapper.update(tfnDtl);
        Tfn tfn = new Tfn();
        tfn.setUnitId(tfnDtl.getUnitId());
        tfn.setTfnNum(tfnDtl.getTfnNum());
        tfnUtil.reCalTfnInfo(tfn);
        return i;
    }

    /**
     * 删除调拨单明细
     * @param tfnDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public int delete(TfnDtl tfnDtl) throws ServiceException {
        int i = tfnDtlMapper.deleteByPrimaryKey(tfnDtl);
        Tfn tfn = new Tfn();
        tfn.setUnitId(tfnDtl.getUnitId());
        tfn.setTfnNum(tfnDtl.getTfnNum());
        tfnUtil.reCalTfnInfo(tfn);
        return i;
    }

    /**
     * 获取调拨单明细列表(无分页)
     * @param tfnDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TfnDtlVo> getTfnDtlList(TfnDtl tfnDtl) throws ServiceException {
        List<TfnDtlVo> list = tfnDtlMapper.getTfnDtlList(tfnDtl);
        return list;
    }
}
