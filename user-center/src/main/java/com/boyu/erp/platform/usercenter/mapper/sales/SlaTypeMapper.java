package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
import com.boyu.erp.platform.usercenter.vo.sales.SlaTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface SlaTypeMapper {
    int deleteByPrimaryKey(String slaType);

    int insert(SlaType record);

    int insertSelective(SlaType record);

    SlaType selectByPrimaryKey(String slaType);

    int updateByPrimaryKeySelective(SlaType record);

    int updateByPrimaryKey(SlaType record);

    List<PurKeyValue> optionGet();

    SlaType selectByPsxType(String psxType);

    List<SlaTypeVo> selectALL(SlaTypeVo vo);

    List<SlaType> listBySlaType(String slaType, String psxType);

    List<SlaType> listByPsxType(String slaType,String psxType);
}