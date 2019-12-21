package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.TfnBxi;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnBxiVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 退销合同类别接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface TfnBxiService {

    public PageInfo<TfnBxiVo> getTfnBxiList(Integer page, Integer size, TfnBxi tfnBxi) throws ServiceException;

    public int insert(TfnBxi tfnBxi) throws ServiceException;

    public int update(TfnBxi tfnBxi) throws ServiceException;

    public int delete(TfnBxi tfnBxi) throws ServiceException;

    public List<TfnBxiVo> getTfnBxiList(TfnBxi tfnBxi) throws ServiceException;
}
