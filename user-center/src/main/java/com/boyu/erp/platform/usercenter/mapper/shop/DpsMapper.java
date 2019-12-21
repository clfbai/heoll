package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.Dps;

import java.util.List;

public interface DpsMapper {
    int deleteByDpsKey(Long dpsId);

    int insertDps(Dps record);

    Dps selectDps(String dpsId);

    int updateDps(Dps record);


    List<Dps> getDps(Dps dps);
}