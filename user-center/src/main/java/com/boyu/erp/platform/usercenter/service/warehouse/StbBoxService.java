package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbBox;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 库存单装箱接口
 * @author: ck
 * @create: 2019-07-02 15:36
 */
public interface StbBoxService {

    public PageInfo<StbBox> getStbBoxList(Integer page, Integer size, StbBox tfnBox) throws ServiceException;

    public int insert(StbBox stbBox) throws ServiceException;

    public List<StbBox> getStbBoxList(StbBox tfnBox) throws ServiceException;

    public int update(StbBox stbBox) throws ServiceException;

    public int delete(StbBox stbBox) throws ServiceException;

    public int deleteAll(StbBox stbBox) throws ServiceException;
}
