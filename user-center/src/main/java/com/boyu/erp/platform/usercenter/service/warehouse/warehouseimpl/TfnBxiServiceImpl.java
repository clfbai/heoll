package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.Tfn;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnBxi;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.TfnBxiMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnBxiService;
import com.boyu.erp.platform.usercenter.utils.warehouse.TfnUtil;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnBxiVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 调拨单配码接口实现
 * @author: ck
 * @create: 2019-06-20 15:36
 */
@Slf4j
@Service
@Transactional
public class TfnBxiServiceImpl implements TfnBxiService {

    @Autowired
    private TfnBxiMapper tfnBxiMapper;

    @Autowired
    private TfnUtil tfnUtil;

    /**
     * 获取调拨单配码列表
     * @param page
     * @param size
     * @param tfnBxi
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<TfnBxiVo> getTfnBxiList(Integer page, Integer size, TfnBxi tfnBxi) throws ServiceException {
        PageHelper.startPage(page, size);
        List<TfnBxiVo> list = tfnBxiMapper.getTfnBxiList(tfnBxi);
        PageInfo<TfnBxiVo> pageInfo = new PageInfo<TfnBxiVo>(list);
        return pageInfo;
    }

    /**
     * 创建调拨单配码
     * @param tfnBxi
     * @return
     * @throws ServiceException
     */
    @Override
    public int insert(TfnBxi tfnBxi) throws ServiceException {
        int i = tfnBxiMapper.insert(tfnBxi);
        Tfn tfn = new Tfn();
        tfn.setUnitId(tfnBxi.getUnitId());
        tfn.setTfnNum(tfnBxi.getTfnNum());
        tfnUtil.reCalTfnInfo(tfn);
        return i;
    }

    /**
     * 更新调拨单配码
     * @param tfnBxi
     * @return
     * @throws ServiceException
     */
    @Override
    public int update(TfnBxi tfnBxi) throws ServiceException {
        int i = tfnBxiMapper.update(tfnBxi);
        Tfn tfn = new Tfn();
        tfn.setUnitId(tfnBxi.getUnitId());
        tfn.setTfnNum(tfnBxi.getTfnNum());
        tfnUtil.reCalTfnInfo(tfn);
        return i;
    }

    /**
     * 删除调拨单配码
     * @param tfnBxi
     * @return
     * @throws ServiceException
     */
    @Override
    public int delete(TfnBxi tfnBxi) throws ServiceException {
        int i = tfnBxiMapper.delete(tfnBxi);
        Tfn tfn = new Tfn();
        tfn.setUnitId(tfnBxi.getUnitId());
        tfn.setTfnNum(tfnBxi.getTfnNum());
        tfnUtil.reCalTfnInfo(tfn);
        return i;
    }

    /**
     * 获取调拨单配码列表
     * @param tfnBxi
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TfnBxiVo> getTfnBxiList(TfnBxi tfnBxi) throws ServiceException {
        List<TfnBxiVo> list = tfnBxiMapper.getTfnBxiList(tfnBxi);
        return list;
    }
}
