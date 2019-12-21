package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.entity.shop.Dps;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.shop.DpsMapper;
import com.boyu.erp.platform.usercenter.service.shop.DpsServicer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DpsServicerImpl implements DpsServicer {
    @Autowired
    private DpsMapper dpsMapper;

    @Override
    public PageInfo<Dps> getDps(Integer page, Integer size, Dps dps, SysUser user) {
        dps.setOwnerId(user.getOwnerId());
        PageHelper.startPage(page, size);
        List<Dps> list = dpsMapper.getDps(dps);
        PageInfo<Dps> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }

    @Override
    public int addDps(Dps dps) {
        return dpsMapper.insertDps(dps);
    }

    @Override
    public int deleteDps(Dps dps) {
        return  dpsMapper.deleteByDpsKey(dps.getDpsId());
    }

    @Override
    public int updateDps(Dps dps) {
        return  dpsMapper.updateDps(dps);
    }
}
