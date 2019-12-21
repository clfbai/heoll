package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchReverse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnitBatchReverseMapper {
    int deleteByPrimaryKey(UnitBatchReverse key);


    int insertSelective(UnitBatchReverse record);

    UnitBatchReverse selectByPrimaryKey(UnitBatchReverse key);

    int updateByPrimaryKeySelective(UnitBatchReverse record);


    List<UnitBatchReverse> selectList(UnitBatchNum unitBatchNum);

    int updateUnitBatchReverseList(@Param("list") List<UnitBatchReverse> reverses);
}