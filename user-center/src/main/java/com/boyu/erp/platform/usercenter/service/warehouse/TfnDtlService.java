package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.TfnDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnDtlVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 退销合同类别接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface TfnDtlService {

    public PageInfo<TfnDtlVo> getTfnDtlList(Integer page, Integer size, TfnDtl tfnDtl) throws ServiceException;

    public int insert(TfnDtl tfnDtl) throws ServiceException;

    public int update(TfnDtl tfnDtl) throws ServiceException;

    public int delete(TfnDtl tfnDtl) throws ServiceException;

    public List<TfnDtlVo> getTfnDtlList(TfnDtl tfnDtl) throws ServiceException;

}
