package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Tfn;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnVo;
import com.github.pagehelper.PageInfo;

/**
 * @program: boyu-platform
 * @description: 退销合同类别接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface TfnService {

    public PageInfo<TfnVo> getTfnList(Integer page, Integer size, Tfn tfn) throws ServiceException;

    public TfnVo getTfnVoByPk(Tfn tfn) throws ServiceException;

    public int delete(Tfn tfn) throws ServiceException;

    public int insert(Tfn tfn) throws ServiceException;

    public int update(Tfn tfn) throws ServiceException;

    public String getMaxTfnNum(Tfn record) throws ServiceException;

    public Tfn getTfnByPk(Tfn record) throws ServiceException;

}
