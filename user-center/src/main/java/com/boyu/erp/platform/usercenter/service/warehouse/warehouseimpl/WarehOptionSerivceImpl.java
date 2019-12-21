package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehOptionSerivce;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehUnitOptionVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class WarehOptionSerivceImpl implements WarehOptionSerivce {

    @Autowired
    private WarehMapper warehMapper;

    /**
     * 功能描述: 查询当前组织的仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 17:11
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehUnitOptionVo> getPageinfo(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user) {
        vo.setUnitId(user.getOwnerId());
        PageHelper.startPage(page, size);
        List<WarehUnitOptionVo> resultList = warehMapper.getUintOption(vo);
        PageInfo<WarehUnitOptionVo> pageInfo = new PageInfo<>(resultList);
        return pageInfo;
    }

    /**
     * 功能描述: 选择组织后查询其仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 17:11
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehUnitOptionVo> getOwnerIdOption(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user) {
        PageHelper.startPage(page, size);
        List<WarehUnitOptionVo> resultList = warehMapper.getOwnerIdOption(vo);
        PageInfo<WarehUnitOptionVo> pageInfo = new PageInfo<>(resultList);
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehUnitOptionVo> getCompanyinfo(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user) {
        vo.setUnitId(user.getOprId());
        PageHelper.startPage(page, size);
        List<WarehUnitOptionVo> resultList = warehMapper.getCompanyOption(vo);
        PageInfo<WarehUnitOptionVo> pageInfo = new PageInfo<WarehUnitOptionVo>(resultList);
        return pageInfo;
    }

    /**
     * 发货方弹窗查询
     */
    @Override
    public PageInfo<WarehUnitOptionVo> getDelivOption(Integer page, Integer size, WarehUnitOptionVo vo, SysUser user) {
        PageHelper.startPage(page, size);
        List<WarehUnitOptionVo> resultList = warehMapper.getDelivOption(vo);
        PageInfo<WarehUnitOptionVo> pageInfo = new PageInfo<WarehUnitOptionVo>(resultList);
        return pageInfo;
    }


    @Override
    public List<OptionVo> getOption(Long warehId) {
        return warehMapper.getOptionVo(warehId);
    }


}
