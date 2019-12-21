package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysPrsnlOwner;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPrsnlOwnerMapper {

    public SysPrsnlOwner selectByPrimaryKey(SysPrsnlOwner key);

    public int insert(SysPrsnlOwner record);

    public int insertSelective(SysPrsnlOwner record);

    public int updateByPrimaryKeySelective(SysPrsnlOwner record);

    public int updateByPrimaryKey(SysPrsnlOwner record);
    /**
     * 根据owner和人员Id查询owner集合
     * @author HHe
     * @date 2019/9/30 12:09
     */
    List<OwnerPrsnlModel> queryListByOwnerAndPrsnl(@Param("ownerPrsnlModelList") List<OwnerPrsnlModel> ownerPrsnlModelList);
}