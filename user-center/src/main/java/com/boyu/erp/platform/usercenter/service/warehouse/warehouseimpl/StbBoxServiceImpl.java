package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbBox;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StbBoxMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.StbBoxService;
import com.boyu.erp.platform.usercenter.utils.warehouse.StbUtil;
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
public class StbBoxServiceImpl implements StbBoxService {

    @Autowired
    private StbBoxMapper stbBoxMapper;

    @Autowired
    private StbUtil stbUtil;

    /**
     * 获取库存单装箱列表
     * @param page
     * @param size
     * @param stbBox
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<StbBox> getStbBoxList(Integer page, Integer size, StbBox stbBox) throws ServiceException {
        PageHelper.startPage(page, size);
        List<StbBox> list = stbBoxMapper.getStbBoxList(stbBox);
        PageInfo<StbBox> pageInfo = new PageInfo<StbBox>(list);
        return pageInfo;
    }

    /**
     * 插入库存单装箱
     * @param stbBox
     * @return
     * @throws ServiceException
     */
    @Override
    public int insert(StbBox stbBox) throws ServiceException {
        int i = stbBoxMapper.insert(stbBox);
        Stb stb = new Stb();
        stb.setUnitId(stbBox.getUnitId());
        stb.setStbNum(stbBox.getStbNum());
        stbUtil.reCalStbInfo(stb, "B");
        return i;
    }

    /**
     * 获取库存单装箱列表 无分页
     * @param tfnBox
     * @return
     * @throws ServiceException
     */
    @Override
    public List<StbBox> getStbBoxList(StbBox tfnBox) throws ServiceException {
        return stbBoxMapper.getStbBoxList(tfnBox);
    }

    /**
     * 更新库存单装箱
     * @param stbBox
     * @return
     * @throws ServiceException
     */
    @Override
    public int update(StbBox stbBox) throws ServiceException {
        int i = stbBoxMapper.updateByPrimaryKey(stbBox);
        Stb stb = new Stb();
        stb.setUnitId(stbBox.getUnitId());
        stb.setStbNum(stbBox.getStbNum());
        stbUtil.reCalStbInfo(stb, "B");
        return i;
    }

    /**
     * 删除单条库存单装箱
     * @param stbBox
     * @return
     * @throws ServiceException
     */
    @Override
    public int delete(StbBox stbBox) throws ServiceException {
        int i = stbBoxMapper.deleteByPrimaryKey(stbBox);
        Stb stb = new Stb();
        stb.setUnitId(stbBox.getUnitId());
        stb.setStbNum(stbBox.getStbNum());
        stbUtil.reCalStbInfo(stb, "B");
        return i;
    }

    /**
     * 根据库存单编号删除所有库存单装箱
     * @param stbBox
     * @return
     * @throws ServiceException
     */
    @Override
    public int deleteAll(StbBox stbBox) throws ServiceException {
        return stbBoxMapper.deleteByPrimaryKey(stbBox);
    }
}
